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
 * 시스템 전반에 걸친 필터이므로 Filterchain
 * TODO: 1개의 필터만 실행되는것을 보장하기위해서는 OncePerRequestFilter 상속받아 구현
 * seijn
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
      List<String> internalPaths = Arrays.asList("/ifroute/api/hydra", "/ifroute/api/overload", "/ifroute/api/ultra");

      // 내부 IP 주소 체크
      InetAddress remoteAddress = exchange.getRequest().getRemoteAddress().getAddress();
      boolean isInternalIp = ((InetAddress) remoteAddress).isLoopbackAddress();
      boolean isContainURI = internalPaths.contains(requestURIPath);
      if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
         Authentication authentication = this.tokenProvider.getAuthentication(jwt);
         exchange.getAttributes().put("authentication", authentication);

         LOG.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestURI);

         return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
      } else if (isContainURI && HttpMethod.GET.equals(requestMethod) && isInternalIp) {
         // 내부에서만 사용되는 경로이고, 메서드가 GET이며, 요청 IP가 내부 IP인 경우에는 인증 없이 진행
         //
         LOG.debug("Internal route and IP detected, no valid JWT token required, uri: {}", requestURI);
         return chain.filter(exchange);
      } else if ("/api/authenticateUrl".equals(requestURIPath)){
         LOG.debug("Request Authenticate URL Call, uri: {}", requestURI);
         return chain.filter(exchange);
      }else {
         LOG.debug("no valid JWT token found, uri: {}", requestURI);
         // 인증 실패 시, 에러 처리 (여기에는 알맞는 에러 처리 로직을 넣으세요)
         throw new RuntimeException("Invalid token");
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
