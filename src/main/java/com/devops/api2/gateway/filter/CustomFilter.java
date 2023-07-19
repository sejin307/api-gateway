package com.devops.api2.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 라우팅 서비스에서 공통으로 활용하는 필터
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
                        /**
                         * TODO:FALLBACK 발생시 여기서 로깅 처리
                         */
                    }));
        };
    }

    public static class Config {
        //Config 설정
    }
}

