package com.devops.api2.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.devops.api2.security.model.User;
import com.devops.api2.security.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 사용자 인증/권한부여
 */
@Component("userDetailsService")
public class UserModelDetailsService implements ReactiveUserDetailsService {

   private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

   private final UserRepository userRepository;

   public UserModelDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public Mono<UserDetails> findByUsername(String username) {
      log.debug("Authenticating user '{}'", username);

      // Email 로그인 처리
      String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
      Pattern pattern = Pattern.compile(emailRegex);
      Matcher matcher = pattern.matcher(username);

      if (matcher.matches()) {
         return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(username)
                 .map(user -> createSpringSecurityUser(username, user))
                 .get().switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User with email " + username + " was not found in the database"))));
      }



      // 일반 사용자명 로그인 처리
      String lowercaseUsername = username.toLowerCase(Locale.ENGLISH);
              //Mono.defer(() -> Mono.error(new UserNotActivatedException("User " + lowercaseUsername + " was not found in the database")));
      return userRepository.findOneWithAuthoritiesByUsername(lowercaseUsername)
              .map(user -> createSpringSecurityUser(lowercaseUsername, user))
              .get().switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User " + lowercaseUsername + " was not found in the database"))));
   }

   private Mono<UserDetails> createSpringSecurityUser(String lowercaseUsername, User user) {
      if (!user.isActivated()) {
         return Mono.error(new UserNotActivatedException("User " + lowercaseUsername + " was not activated"));
      }

      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getName()))
              .collect(Collectors.toList());

      return Mono.just(new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              grantedAuthorities
      ));
   }


}