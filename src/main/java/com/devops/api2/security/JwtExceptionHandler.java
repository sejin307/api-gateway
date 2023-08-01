package com.devops.api2.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt Exception 핸들링
 */
@Component
public class JwtExceptionHandler {
    public static Mono<Void> handleException(ServerWebExchange exchange, Throwable exception) {
        return Mono.defer(() -> {
            ServerHttpResponse response = exchange.getResponse();
            Map<String, String> errorMap = new HashMap<>();
            HttpStatus status;

            if (exception instanceof AuthenticationException) {
                status = HttpStatus.UNAUTHORIZED;
                errorMap.put("httpstatus", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                errorMap.put("error", "Unauthorized");
            } else if (exception instanceof AccessDeniedException) {
                status = HttpStatus.FORBIDDEN;
                errorMap.put("httpstatus", String.valueOf(HttpStatus.FORBIDDEN.value()));
                errorMap.put("error", "Access Denied");
            }else{
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                errorMap.put("httpstatus", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                errorMap.put("error", "Internal Server Error");
            }

            byte[] bytes;
            try {
                bytes = new ObjectMapper().writeValueAsBytes(errorMap);
            } catch (JsonProcessingException e) {
                return Mono.error(e);
            }

            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(status);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return response.writeWith(Mono.just(buffer)).then(Mono.empty());
        });
    }
}
