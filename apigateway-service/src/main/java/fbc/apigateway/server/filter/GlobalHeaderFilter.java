package fbc.apigateway.server.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.headers.observation.GatewayContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.apigateway.server.config
 * @fileName : GlobalHeaderFilter
 * @date : 24. 9. 9.
 * @description :
 * ===========================================================
 */
@Component
@Slf4j
public class GlobalHeaderFilter extends AbstractGatewayFilterFactory<GlobalHeaderFilter.Config>  {

    public GlobalHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Pre Filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage : {}", config.getBaseMessage());

            /** request url init **/
            String uri = getUri(exchange);
//            GatewayContext context = exchange.getApplicationContext()
            if(config.isPreLogger()){
                log.info("## Global Filter start : request url, id : [{}], [{}]", uri, request.getId());
            }

            // Custom Pos Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()){
                    log.info("Global Filter end : response code -> {}", response.getStatusCode());
                }
            }));
        };
    }

    private String getUri(ServerWebExchange exchange) {
        LinkedHashSet<URI> uris = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        try{
            return Optional.ofNullable(uris)
                    .orElseGet(LinkedHashSet::new)
                    .stream()
                    .findFirst()
                    .map(uri -> uri.getPath())
                    .orElseGet(() -> "");
        }catch(Exception e){
           return "";
        }
    }

    @Data
    public static class Config {
        // Put the configuration properties
        private String baseMessage;
        private boolean isPreLogger;
        private boolean isPostLogger;
    }
}
