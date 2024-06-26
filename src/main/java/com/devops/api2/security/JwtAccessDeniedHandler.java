package com.devops.api2.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * JWT(접근권한) Exception 핸들링 처리
 */
@Component
public class JwtAccessDeniedHandler implements ServerAccessDeniedHandler {

   @Override
   public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException accessDeniedException) {
      // This is invoked when user tries to access a secured REST resource without the necessary authorization
      // We should just send a 403 Forbidden response because there is no 'error' page to redirect to
      // Here you can place any message you want

      /**
       * TODO: api호출하는측에 전달하는 Response Value를 여기서 처리
       */
      return Mono.fromRunnable(() -> {
         exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
         // further steps to add your custom error message or body...
      });
   }
}
