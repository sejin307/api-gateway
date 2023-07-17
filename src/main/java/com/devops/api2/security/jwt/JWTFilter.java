package com.devops.api2.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

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
      String jwt = resolveToken(exchange);
      String requestURI = exchange.getRequest().getURI().toString();
      String requestURIPath = exchange.getRequest().getPath().toString();
      HttpMethod requestMethod = exchange.getRequest().getMethod();

      // 내부에서만 사용하는 경로를 리스트로 관리
      List<String> internalPaths = Arrays.asList("/actuator/metrics/hydraCircuitBreaker");

      // 내부 IP 주소 체크
      InetAddress remoteAddress = exchange.getRequest().getRemoteAddress().getAddress();
      boolean isInternalIp = remoteAddress.isLoopbackAddress();
      boolean isContainURI = internalPaths.contains(requestURIPath);
      if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
         Authentication authentication = this.tokenProvider.getAuthentication(jwt);
         exchange.getAttributes().put("authentication", authentication);

         LOG.debug("set Authentication context '{}', uri: {}", authentication.getName(), requestURI);

         return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
      } else if (isContainURI && HttpMethod.GET.equals(requestMethod) && isInternalIp) {
         LOG.debug("Internal Route and IP Dectected / Do not authorize  OR System Call URI: {}", requestURI);
         return chain.filter(exchange);
      } else if ("/api/authenticateUrl".equals(requestURIPath)){
         LOG.debug("Request JWT Authenticate URL Call, uri: {}", requestURI);
         return chain.filter(exchange);
      } else if("/actuator".equals(requestURIPath)){
         LOG.debug("Actuator Call", requestURI);
         return chain.filter(exchange);
      }else {
         return chain.filter(exchange);
         /*LOG.debug("No valid JWT OR JWT is Null, uri: {}", requestURI);
         // 인증 실패 시, 에러 처리
         throw new RuntimeException("Invalid token");*/
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
