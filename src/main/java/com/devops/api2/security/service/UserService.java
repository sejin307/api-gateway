package com.devops.api2.security.service;

import com.devops.api2.security.SecurityUtils;
import com.devops.api2.security.model.User;
import com.devops.api2.security.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * User정보 및 권한 조회
 */
@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }


   /**
    * DATA READONLY,NO LOCK,NO ROLLBACK
    * @return
    */
   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

}
