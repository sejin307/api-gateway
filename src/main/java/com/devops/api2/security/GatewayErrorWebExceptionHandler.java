package com.devops.api2.security;

import io.jsonwebtoken.JwtException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * SpringBoot에서 감지한 Exception처리를 하지않고, 커스텀으로 정의한 Exception처리를 하기위함.
 * -2는 SpringBoot에서의 처리보다 더 높은 우선순위를 주기위함.
 */
@Component
@Order(-2)
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return JwtExceptionHandler.handleException(exchange, ex);
    }
}
