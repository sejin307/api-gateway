package com.devops.api2.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * JWT(접근권한) Exception 핸들링 처리
 */
@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

   @Override
   public Mono<Void> commence(ServerWebExchange exchange,
                              AuthenticationException authException) {
      // This is invoked when user tries to access a secured REST resource without supplying any credentials
      // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
      // Here you can place any message you want

      /**
       * TODO: api호출하는측에 전달하는 Response Value를 여기서 처리
       */
      return Mono.fromRunnable(() -> {
         exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
         // further steps to add your custom error message or body...
      });
   }
}
