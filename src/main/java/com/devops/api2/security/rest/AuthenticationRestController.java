package com.devops.api2.security.rest;

import com.devops.api2.security.CryptoUtils;
import com.devops.api2.security.jwt.JWTFilter;
import com.devops.api2.security.jwt.TokenProvider;
import com.devops.api2.security.rest.dto.LoginDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 인증(FORM,URL) 처리
 * JWTToken 생성,검증
 * TODO: 토큰 갱신
 * AUTHOR : sejin
 */
@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

   private final TokenProvider tokenProvider;

   private final AuthenticationManagerBuilder authenticationManagerBuilder;

   public static final String AUTHORIZATION_HEADER = "Authorization";

   public AuthenticationRestController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
      this.tokenProvider = tokenProvider;
      this.authenticationManagerBuilder = authenticationManagerBuilder;
   }

   /**
    * form 인증
    * @param loginDto
    * @return
    */
   @PostMapping("/authenticate")
   public ResponseEntity<JWTToken> authorize(@Validated @RequestBody LoginDto loginDto) {
      UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
      String jwt = tokenProvider.createToken(authentication, rememberMe);

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
   }

   /**
    * URL인증
    * @param data
    * @return
    */
   @PostMapping("/authenticateUrl/{data}")
   public ResponseEntity<JWTToken> authorize(@PathVariable String data) {
      String [] decodeStr = decodeString(data);
      String parts1 = decodeStr[0];
      String parts2 = decodeStr[1];

      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(parts1, parts2);

      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      //boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
      boolean rememberMe = false;
      String jwt = tokenProvider.createToken(authentication, rememberMe);

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
   }

   @PostMapping("/authenticate/valid-token")
   public ResponseEntity<JWTTokenValid> authorize(HttpServletRequest httpServletRequest) {
      String jwt = resolveToken(httpServletRequest);
      boolean isValid = tokenProvider.validateToken(jwt);

      return new ResponseEntity<>(new JWTTokenValid(jwt,isValid), HttpStatus.OK);
   }



   /**
    * 인증 > JWT 생성 > JWT 리턴
    */
   static class JWTToken {

      private String idToken;

      JWTToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
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

   private String resolveToken(HttpServletRequest request) {
      String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }
      return null;
   }

   private String [] decodeString(String data){
      boolean isValidStr = true;
      String decStr = CryptoUtils.decrypt(data);//admin/admin
      String[] parts = decStr.split("/");

      return parts;
   }
}
