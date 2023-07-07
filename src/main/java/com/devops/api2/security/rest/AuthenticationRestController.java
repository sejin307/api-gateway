package com.devops.api2.security.rest;

import com.devops.api2.security.CryptoUtils;
import com.devops.api2.security.jwt.JWTFilter;
import com.devops.api2.security.jwt.TokenProvider;
import com.devops.api2.security.rest.dto.LoginDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;


/**
 * 인증(FORM,URL) 처리
 * JWTToken 생성,검증,?갱신?
 * AUTHOR : sejin
 */
@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

   private final TokenProvider tokenProvider;
   private final ReactiveAuthenticationManager authenticationManager;
   private final ReactiveUserDetailsService myReactiveUserDetailsService;
   public static final String AUTHORIZATION_HEADER = "Authorization";

   public AuthenticationRestController(TokenProvider tokenProvider,
                                       ReactiveAuthenticationManager authenticationManager,
                                       ReactiveUserDetailsService myReactiveUserDetailsService) {
      this.tokenProvider = tokenProvider;
      this.authenticationManager = authenticationManager;
      this.myReactiveUserDetailsService = myReactiveUserDetailsService;
   }

   /**
    * form 인증
    * @param loginDto
    */
   @PostMapping("/authenticate")
   public Mono<ResponseEntity<JWTToken>> authorize(@Validated @RequestBody LoginDto loginDto) {
      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

      return authenticationManager.authenticate(authenticationToken)
              .flatMap(authentication -> {
                 SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                 securityContext.setAuthentication(authentication);
                 return ReactiveSecurityContextHolder.getContext()
                         .flatMap(context -> {
                            context.setAuthentication(authentication);
                            return Mono.just(securityContext);
                         });
              })
              .map(securityContext -> {
                 boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
                 String jwt = tokenProvider.createToken(securityContext.getAuthentication(), rememberMe);
                 HttpHeaders httpHeaders = new HttpHeaders();
                 httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
                 return ResponseEntity.ok().headers(httpHeaders).body(new JWTToken(jwt, HttpStatus.OK));
              })
              .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
   }



   /**
    * URL인증
    * @return
    */
   @GetMapping("/authenticateUrl")
   public Mono<ResponseEntity<JWTToken>> authorize(@RequestParam String data) {
      String[] decodeStr = decodeString(data);
      String parts1 = decodeStr[0];
      String parts2 = decodeStr[1];

      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(parts1, parts2);

      return this.authenticationManager.authenticate(authenticationToken)
              .flatMap(authentication -> {
                 SecurityContextHolder.getContext().setAuthentication(authentication);
                 boolean rememberMe = false;
                 String jwt = tokenProvider.createToken(authentication, rememberMe);

                 HttpHeaders httpHeaders = new HttpHeaders();
                 httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
                 return Mono.just(new ResponseEntity<>(new JWTToken(jwt,HttpStatus.OK), httpHeaders, HttpStatus.OK));
              })
              .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
   }


   @PostMapping("/authenticate/valid-token")
   public Mono<ResponseEntity<JWTTokenValid>> authorize(ServerWebExchange exchange) {
      String jwt = resolveToken(exchange);
      boolean isValid = tokenProvider.validateToken(jwt);

      return Mono.just(new ResponseEntity<>(new JWTTokenValid(jwt,isValid), HttpStatus.OK));
   }

   /**
    * 인증 > JWT 생성 > JWT 리턴
    */
   public static class JWTToken {

      private String idToken;
      private final String status;
      private final HttpStatus httpStatus;

      JWTToken(String idToken, HttpStatus httpStatus) {
         this.idToken = idToken;
         this.httpStatus = httpStatus;
         if(httpStatus.is2xxSuccessful()){
            this.status = "success";
         } else {
            this.status = "error";
         }
      }

      @JsonProperty("id_token")
      public String getIdToken() {
         return idToken;
      }

      public void setIdToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("status")
      public String getStatus() {
         return status;
      }

      @JsonProperty("http_status")
      public String getHttpStatus() {
         return httpStatus.toString();
      }
   }


   static class JWTTokenValid{
      private boolean isValidToken;
      private String idToken;

      JWTTokenValid(String idToken,boolean isValidToken) {
         this.idToken = idToken;
         this.isValidToken = isValidToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("isValidToken")
      boolean getIsValidToken() { return isValidToken; }

      void setValidToken(boolean isValidToken) {
         this.isValidToken = isValidToken;
      }
   }

   private String resolveToken(ServerWebExchange exchange) {
      String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }
      return null;
   }


   private String [] decodeString(String data){
      String decStr = CryptoUtils.decrypt(data);//admin/admin
      String[] parts = decStr.split("/");

      return parts;
   }
}
