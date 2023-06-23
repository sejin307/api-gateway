package com.devops.api2.config;

import com.devops.api2.security.JwtAccessDeniedHandler;
import com.devops.api2.security.JwtAuthenticationEntryPoint;
import com.devops.api2.security.jwt.JWTConfigurer;
import com.devops.api2.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * SpringSecurity 초기설정
 * SpringBoot 3.x 이상에서는 WebSecurityConfigurerAdapter를 사용할수없고, configure 세팅이 많이다름.
 * Boot 버전확인 필요.
 * sejin307
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }


   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   /**
    * static pass
    * @param web
    */
   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")

         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
            //"/h2-console/**"   H2 > MYSQL 변경 / application.yml h2 enable true 로 변경해야함.
         );
   }

   // security 초기설정
   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         .csrf().disable()//JWTtoken으로 관리하므로 설정X

         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         .exceptionHandling()
         .authenticationEntryPoint(authenticationErrorHandler)
         .accessDeniedHandler(jwtAccessDeniedHandler)

         .and()
         .headers()
         .frameOptions()
         .sameOrigin()

         // create no session
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션정책 설정. 상태X

         .and()
         .authorizeRequests()
         .antMatchers("/api/authenticate").permitAll()//LOGIN FORM인증
         .antMatchers("/api/authenticateUrl/{data}").permitAll()//URL인증
         .antMatchers("/api/authenticate/valid-token").permitAll()//JWT검증
         .antMatchers("/proxy/send").permitAll()//RabbitMQ 테스트

         .antMatchers("/api/person").hasAuthority("ROLE_USER") //일반권한만 접근
         .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN") //관리자권한만 접근

         .anyRequest().authenticated()

         .and()
         .apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}
