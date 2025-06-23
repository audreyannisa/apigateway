package com.example.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
