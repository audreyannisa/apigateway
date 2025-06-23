package com.example.apigateway;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
//public abstract class CustomFilter implements GatewayFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("Before request processing");
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            System.out.println("After response processing");
//        }));
//    }
//}

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import java.util.logging.Logger;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    Logger logger = Logger.getLogger(CustomFilter.class.getName());

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            String x = exchange.getRequest().getHeaders()
            logger.info(config.getBeforeMessage() + path);
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> logger.info(config.getAfterMessage() + path)));
        };
    }

    public static class Config {
        private String afterMessage = "Request:";
        private String beforeMessage = "Incoming request:";

        public String getAfterMessage() {
            return afterMessage;
        }

        public String getBeforeMessage() {
            return beforeMessage;
        }

        public void setAfterMessage(String afterMessage) {
            this.afterMessage = afterMessage;
        }

        public void setBeforeMessage(String beforeMessage) {
            this.beforeMessage = beforeMessage;
        }


    }
}
