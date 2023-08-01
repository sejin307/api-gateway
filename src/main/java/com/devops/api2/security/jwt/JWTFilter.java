package com.devops.api2.security.jwt;

import com.devops.api2.security.JwtExceptionHandler;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetAddress;

/**
 * URI 필터링
 * 시스템 전반에 걸친 필터
 */
public class JWTFilter implements WebFilter {

   private static final Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

   public static final String AUTHORIZATION_HEADER = "Authorization";

   private final TokenProvider tokenProvider;

   public JWTFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      return Mono.defer(() -> {
         String jwt = resolveToken(exchange);
         String requestURI = exchange.getRequest().getURI().toString();
         String requestURIPath = exchange.getRequest().getPath().toString();

         InetAddress remoteAddress = exchange.getRequest().getRemoteAddress().getAddress();
         String internalHeader = exchange.getRequest().getHeaders().getFirst("Internal-Route-Request");
         boolean isInternalIp = remoteAddress.isLoopbackAddress();

         try {
            if (StringUtils.hasText(jwt)) {
               this.tokenProvider.validateToken(jwt);
               Authentication authentication = this.tokenProvider.getAuthentication(jwt);
               exchange.getAttributes().put("authentication", authentication);
               LOG.debug("set Authentication context '{}', uri: {}", authentication.getName(), requestURI);
               return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            }
            // 이하 다른 조건에 따른 처리
         } catch (JwtException e) {
            return JwtExceptionHandler.handleException(exchange, new Throwable())
                    .then(Mono.error(new RuntimeException("Exception occurred during request processing")));
         }

         /*if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            exchange.getAttributes().put("authentication", authentication);

            LOG.debug("set Authentication context '{}', uri: {}", authentication.getName(), requestURI);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
         }*/

         if (StringUtils.hasText(internalHeader) && isInternalIp) {
            LOG.debug("Internal Route and IP Dectected", requestURI);
            return chain.filter(exchange);
         } else if ("/api/authenticate".equals(requestURIPath) || "/api/authenticateUrl".equals(requestURIPath) || "/actuator".equals(requestURIPath) || "/".equals(requestURIPath)) {
            LOG.debug("Request JWT Authenticate URL Call, uri: {}", requestURI);
            return chain.filter(exchange);
         } else {
            LOG.debug("No valid JWT OR JWT is Null, uri: {}", requestURI);
            return JwtExceptionHandler.handleException(exchange, new Throwable())
                    .then(Mono.error(new RuntimeException("Exception occurred during request processing")));
            /*return Mono.error(new RuntimeException("Invalid token"));*/
         }
      });
   }


   private String resolveToken(ServerWebExchange exchange) {
      String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }
      return null;
   }
}
