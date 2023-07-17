package com.devops.api2.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 비활성화된 유저가 인증을 시도할때 exception
 */
public class UserNotActivatedException extends AuthenticationException {

   private static final long serialVersionUID = -1126699074574529145L;

   public UserNotActivatedException(String message) {
      super(message);
   }
}
