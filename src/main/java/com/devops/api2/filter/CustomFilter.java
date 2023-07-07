package com.devops.api2.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Add your custom logic here

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> CUSTOM FILTER <~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" );
        return (exchange, chain) -> {
            // pre-processing
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> CUSTOM FILTER <~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" );
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        // post-processing
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~> CUSTOM FILTER <~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" );
                    }));
        };
    }

    public static class Config {
        // Add configuration properties here
    }
}

