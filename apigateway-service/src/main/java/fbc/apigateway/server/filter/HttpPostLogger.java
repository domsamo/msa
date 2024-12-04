package fbc.apigateway.server.filter;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.apigateway.server.filter
 * @fileName : HttpPostLogger
 * @date : 24. 12. 3.
 * @description : Gateway 의 request, response 로그를 남긴다.
 * ===========================================================
 */
@Component
@Slf4j
public class HttpPostLogger {

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    public void run(ServerWebExchange exchange) {
        log.info("############## PostLogging.run ###########");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        byte[] requestBody = (byte[])exchange.getAttributes().get("REQUEST_BODY");
        byte[] responseBody = (byte[])exchange.getAttributes().get("RESPONSE_BODY");

        log.info("requestBody = {}", new String(requestBody));
        log.info("responseBody = {}", new String(responseBody));

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("requestBody", new String(requestBody));
        logMap.put("responseBody", new String(responseBody));

//        transLog(logMap);

    }

    /**
     * 원격 시스템으로 로그전송
     * @param logMap
     */
    private void transLog(Map<String, Object> logMap) {
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        Mono<String> result = webClient
                .post()
                .uri("http://localhost:8080/test")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(logMap)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)));

        result.subscribe(response -> {
            log.info("Success");
        }, e -> {
            log.error("Failure");
        });
    }
}
