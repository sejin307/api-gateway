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


    @Override
    public GatewayFilter apply(Config config) {
        // filter
        return (exchange, chain) -> {
            // 요청 > 캐시 key생성
            String cacheKey = createCacheKey(exchange.getRequest());

            Cache cache = cacheManager.getCache("api2Cache");

            // 캐시키 > 캐시응답 가져옴
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            if (valueWrapper != null) {
                // 캐시된 응답이 있으면 반환
                List<DataBuffer> cachedResponse = (List<DataBuffer>) valueWrapper.get();
                return exchange.getResponse().writeWith(Flux.fromIterable(cachedResponse));
            }

            // 캐시된 응답이 없으면 새로운 응답 생성
            ServerHttpResponse originalResponse = exchange.getResponse();
            List<DataBuffer> dataBuffers = new ArrayList<>();
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    Flux<? extends DataBuffer> buffer = Flux.from(body);

                    // 응답 본문을 작성하면서, 본문 데이터를 우리의 목록에 복사
                    return super.writeWith(buffer.doOnNext(dataBuffer -> {
                        // dataBuffer 딥카피 > 목록에 추가
                        byte[] array = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(array);
                        DataBufferUtils.release(dataBuffer);

                        DataBuffer bufferCopy = originalResponse.bufferFactory().wrap(array);
                        dataBuffers.add(bufferCopy);
                    }));
                }
            };

            // 교환필터링 > Filter 체이닝
            return chain.filter(exchange.mutate().response(decoratedResponse).build())
                    .then(Mono.fromRunnable(() -> {
                        // 체인이 완료된 후, 상태 코드가 성공을 나타내는 경우 응답을 캐시합니다.
                        if (decoratedResponse.getStatusCode().is2xxSuccessful()) {
                            cache.put(cacheKey, dataBuffers);
                        }
                    }));
        };
    }



    private String createCacheKey(ServerHttpRequest request) {
        // 요청 URL 또는 특정 요청 파라미터 등을 기반으로 고유한 캐시 키 생성
        // 예: request.getURI().toString()
        return request.getURI().toString();
    }

    private ServerHttpResponse createCachedResponse(ServerHttpResponse originalResponse) {
        // 응답 복제
        ServerHttpResponse responseToCache = new ServerHttpResponseDecorator(originalResponse);
        responseToCache.getHeaders().add("X-Custom-Cache", "true");
        return responseToCache;
    }

    public static class Config {
        // Add configuration properties here
    }
}

