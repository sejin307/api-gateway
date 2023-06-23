package com.devops.api2.security.rest.dto;

import org.wildfly.common.annotation.NotNull;



/**
 * FORM 로그인 > 사용자정보  dto 세팅
 * sejin
 */
public class LoginDto {

   @NotNull
   private String username;

   @NotNull
   private String password;

   private Boolean rememberMe;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Boolean isRememberMe() {
      return rememberMe;
   }

   public void setRememberMe(Boolean rememberMe) {
      this.rememberMe = rememberMe;
   }

   @Override
   public String toString() {
      return "LoginVM{" +
         "username='" + username + '\'' +
         ", rememberMe=" + rememberMe +
         '}';
   }
}
