package com.devops.api2.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * URI 필터링
 * 시스템 전반에 걸친 필터이므로 Filterchain
 * TODO: 1개의 필터만 실행되는것을 보장하기위해서는 OncePerRequestFilter 상속받아 구현
 * seijn
 */
public class JWTFilter implements WebFilter {

   private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

   public static final String AUTHORIZATION_HEADER = "Authorization";

   private TokenProvider tokenProvider;

   public JWTFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      String jwt = resolveToken(exchange);
      String requestURI = exchange.getRequest().getURI().toString();

      if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
         Authentication authentication = this.tokenProvider.getAuthentication(jwt);
         exchange.getAttributes().put("authentication", authentication);

         LOG.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestURI);

         return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
      } else {
         LOG.debug("no valid JWT token found, uri: {}", requestURI);
         return chain.filter(exchange);
      }
   }

   private String resolveToken(ServerWebExchange exchange) {
      String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }
      return null;
   }
}
