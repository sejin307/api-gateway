package com.devops.api2.gateway.filter;

import org.reactivestreams.Publisher;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {


    private final CacheManager cacheManager;

    public CustomFilter(CacheManager cacheManager) {
        super(Config.class);
        this.cacheManager = cacheManager;
    }

    /**
     * 여기서 실제 filter 로직 실행
     * @param config
     * @return
     */
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

