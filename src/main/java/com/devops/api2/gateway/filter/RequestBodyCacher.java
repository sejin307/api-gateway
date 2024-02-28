package com.devops.api2.gateway.filter;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RequestBodyCacher implements WebFilter {

    private static final byte[] EMPTY_BYTES = new byte[0];

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return DataBufferUtils
                .join(exchange.getRequest().getBody())
                .map(databuffer -> {

                    final byte[] bytes = new byte[databuffer.readableByteCount()];

                    DataBufferUtils.release(databuffer.read(bytes));

                    return bytes; // 한번 읽기가 가능한 리퀘스트 바디 데이터를 미리 가져옴
                })
                .defaultIfEmpty(EMPTY_BYTES)
                .flatMap(bytes -> {

                    // 읽어온 데이터를 커스텀 리퀘스트 데코레이더를 통해 캐싱
                    final RequestBodyDecorator decorator = new RequestBodyDecorator(exchange, bytes);

                    // 캐싱된 리퀘스트 데이터를 가지고 있는 리퀘스트 데코레이터를 뮤테이션으로 등록
                    return chain.filter(exchange.mutate().request(decorator).build());
                });
    }
}

// ServerWebExchange mutation을 위한 커스텀 리퀘스트 데코레이터;
// -> 실질적인 리퀘스트 바디 데이터를 바이트 형태로 필드로써 가지고 있으며,
// 리퀘스트 바디 필요시 호출하는 getBody 메소드를 오버라이드하여
// 캐싱된 바이트로부터 데이터를 매번 읽어오는 Flux 퍼블리셔를 리턴하도록 한다.
class RequestBodyDecorator extends ServerHttpRequestDecorator {

    private final byte[] bytes;
    private final ServerWebExchange exchange;

    public RequestBodyDecorator(ServerWebExchange exchange, byte[] bytes) {

        super(exchange.getRequest());
        this.bytes = bytes;
        this.exchange = exchange;
    }

    @Override
    public Flux<DataBuffer> getBody() {

        return bytes==null||bytes.length==0?
                Flux.empty(): Flux.just(exchange.getResponse().bufferFactory().wrap(bytes));
    }
}