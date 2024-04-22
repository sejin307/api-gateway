package com.devops.api2.gateway.filter;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseBodyCacher implements WebFilter {

    private static final byte[] EMPTY_BYTES = new byte[0];


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return DataBufferUtils
                .join(exchange.getRequest().getBody())
                .map(databuffer -> {
                    final byte[] bytes = new byte[databuffer.readableByteCount()];
                    databuffer.read(bytes);
                    DataBufferUtils.release(databuffer);
                    return bytes;
                })
                .defaultIfEmpty(EMPTY_BYTES)
                .flatMap(bytes -> {
                    final RequestBodyDecorator requestBodyDecorator = new RequestBodyDecorator(exchange, bytes);
                    ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {

                        @Override
                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                            return DataBufferUtils.join(body)
                                    .flatMap(dataBuffer -> {
                                        byte[] array = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(array);
                                        DataBufferUtils.release(dataBuffer);

                                        String responseBody = new String(array, StandardCharsets.UTF_8);
                                        String reqId = exchange.getRequest().getId();
                                        String reqUriPath = String.valueOf(exchange.getRequest().getPath());
                                        String reqMethod = String.valueOf(exchange.getRequest().getMethod());

                                        /*System.out.println("~~~~~~~~bodyCacher ReqId : " + reqId);
                                        System.out.println("~~~~~~~~bodyCacher reqUriPath : " + reqUriPath);
                                        System.out.println("~~~~~~~~bodyCacher reqMethod : " + reqMethod);*/

                                        if(!reqUriPath.contains("/api2") && "POST".equals(reqMethod)){
                                            logResponse(exchange, responseBody);
                                        }

                                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(array);
                                        return super.writeWith(Flux.just(buffer));
                                    });
                        }
                    };

                    return chain.filter(exchange.mutate().request(requestBodyDecorator).response(responseDecorator).build());
                });
    }

    private void logResponse(ServerWebExchange exchange, String responseBody) {
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~> Decorator ResponseBody : " + responseBody);
        //exchange.getAttributes().put("responseBody", responseBody);
    }

    private static class RequestBodyDecorator extends ServerHttpRequestDecorator {
        private final byte[] bytes;
        private final ServerWebExchange exchange;

        public RequestBodyDecorator(ServerWebExchange exchange, byte[] bytes) {
            super(exchange.getRequest());
            this.bytes = bytes;
            this.exchange = exchange;
        }

        @Override
        public Flux<DataBuffer> getBody() {
            return bytes == null || bytes.length == 0 ?
                    Flux.empty() : Flux.just(exchange.getResponse().bufferFactory().wrap(bytes));
        }
    }
}
