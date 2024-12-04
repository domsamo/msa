package fbc.apigateway.server.filter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.apigateway.server.filter
 * @fileName : GlobalFilterinit
 * @date : 24. 12. 3.
 * @description :
 * ===========================================================
 */
@Slf4j
@Component
public class GlobalInitFilter implements GlobalFilter, Ordered {
    private static final byte[] EMPTY_BYTES = new byte[0];

    @Autowired
    private HttpPostLogger postLogging;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("############## GlobalFilterInit ###################");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                // response 처리후 로깅 처리
                if(body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().map(dataBufferList -> {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        dataBufferList.forEach(dataBuffer -> {
                            byte[] resBodyBytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(resBodyBytes);
                            try{
                                baos.write(resBodyBytes);
                            }catch (IOException e){
                                log.error(e.getMessage());
                            }finally {
                                DataBufferUtils.release(dataBuffer);
                            }
                        });

                        byte[] responseBytes = baos.toByteArray();
                        exchange.getAttributes().put("RESPONSE_BODY", responseBytes);

                        //logging
                        postLogging.run(exchange);
                        return bufferFactory.wrap(responseBytes);
                    }));
                }
                return super.writeWith(body);
            }
        };

        return DataBufferUtils.join(exchange.getRequest().getBody())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    DataBufferUtils.release(dataBuffer.read(bytes));
                    return bytes; // 한번 읽기가 가능한 리퀘스트 바디 데이터를 미리 가져옴
                })
                .defaultIfEmpty(EMPTY_BYTES)
                .flatMap(bytes -> {
                    if(bytes == null || bytes.length < 1) {
                        return chain.filter(exchange);
                    }

                    String reqBodyStr = new String(bytes);
                    exchange.getAttributes().put("REQUEST_BODY", bytes);
                    // 읽어온 데이터를 커스텀 리퀘스트 데코레이더를 통해 캐싱
                    ServerHttpRequest requestDecorator = new ServerHttpRequestDecorator(request) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return DataBufferUtils.read(new ByteArrayResource(bytes),
                                    new NettyDataBufferFactory(ByteBufAllocator.DEFAULT),
                                    bytes.length);
                        }
                    };
                    // 캐싱된 리퀘스트 데이터를 가지고 있는 리퀘스트 데코레이터를 뮤테이션으로 등록
                    return chain.filter(exchange.mutate()
                                    .request(requestDecorator)
                                    .response(responseDecorator)
                                    .build());
                });
    }

    @Override
    public int getOrder() {
        return -2;
    }   // -1 : response write filter
}
