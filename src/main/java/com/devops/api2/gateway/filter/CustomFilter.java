package com.devops.api2.gateway.filter;

import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * TODO:Retry 설정 Cache에서 기존 요청정보를 가져와서 call하면 성능향상, 필요에따라 구현
 */
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    /**
     * 여기서 실제 filter 로직 실행
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {//before-processing

        return (exchange, chain) -> {//pre-processing

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> { //post-processing

                    }));
        };
    }

    public static class Config {
        //Config 설정
    }
}

