package com.devops.api2.gateway.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;


@Component
public class CustomCircuitBreakerGatewayFilterFactory
        extends AbstractGatewayFilterFactory<CustomCircuitBreakerGatewayFilterFactory.Config> {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public CustomCircuitBreakerGatewayFilterFactory(CircuitBreakerRegistry circuitBreakerRegistry) {
        super(Config.class);
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public GatewayFilter apply(Config config) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(config.getName());

        return (exchange, chain) -> chain.filter(exchange)
                .transform(CircuitBreakerOperator.of(circuitBreaker));
    }



    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public Config setName(String name) {
            this.name = name;
            return this;
        }
    }
}

