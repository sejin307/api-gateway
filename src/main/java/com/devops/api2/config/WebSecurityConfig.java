package com.devops.api2.config;

import com.devops.api2.security.JwtAccessDeniedHandler;
import com.devops.api2.security.JwtAuthenticationEntryPoint;
import com.devops.api2.security.jwt.JWTConfigurer;
import com.devops.api2.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.reactive.CorsWebFilter;

/**
 * SpringSecurity 초기설정
 * SpringBoot 3.x 이상에서는 WebSecurityConfigurerAdapter 를 사용할수없고, configure 세팅이 많이다름.
 * Boot 버전확인 필요.
 */
//@EnableWebSecurity
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)  //전역 메소드보안
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig{

   private final TokenProvider tokenProvider;
   private final CorsWebFilter corsWebFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsWebFilter corsWebFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler) {
      this.tokenProvider = tokenProvider;
      this.corsWebFilter = corsWebFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }


   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   /* security 초기설정 >> JWTConfigurer 로 변경
   public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
      http.csrf().disable()

      // Since `addFilterBefore` isn't available, you need to find another way to add the corsFilter.
      // .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling()
      .authenticationEntryPoint(authenticationErrorHandler)
      .accessDeniedHandler(jwtAccessDeniedHandler)
      // session management is stateless by default in webflux
      // .and()
      // .sessionManagement()
      // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeExchange()
      .pathMatchers("/api/authenticate", "/api/authenticateUrl", "/api/authenticate/valid-token").permitAll()
      .pathMatchers("/api/person").hasRole("USER") // 일반권한만 접근
      .pathMatchers("/api/hidden message").hasRole("ADMIN") // 관리자권한만 접근
      .anyExchange().authenticated()

      // apply method is not available in WebFlux, you might want to integrate JWT authentication differently
      // .and()
      // .apply(securityConfigurerAdapter());
      ;

      return http.build();
   }*/




   /*private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider, authenticationErrorHandler, jwtAccessDeniedHandler, userDetailsService);
   }*/
}
