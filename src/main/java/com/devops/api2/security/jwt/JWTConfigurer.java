package com.devops.api2.security.jwt;

import com.devops.api2.security.JwtAccessDeniedHandler;
import com.devops.api2.security.JwtAuthenticationEntryPoint;
import com.devops.api2.security.UserModelDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class JWTConfigurer {

    private TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserModelDetailsService userDetailsService;

    public JWTConfigurer(TokenProvider tokenProvider,
                         JwtAuthenticationEntryPoint authenticationErrorHandler,
                         JwtAccessDeniedHandler jwtAccessDeniedHandler, UserModelDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        JWTFilter jwtFilter = new JWTFilter(tokenProvider);
        http.addFilterAt(jwtFilter, SecurityWebFiltersOrder.HTTP_BASIC);
        http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exceptionHandling ->
                              exceptionHandling
                                      .authenticationEntryPoint(authenticationErrorHandler)
                                      .accessDeniedHandler(jwtAccessDeniedHandler))
        .authorizeExchange(authorizeExchange ->
            authorizeExchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/", "/*.html","/*.css","/*.js","/h2-console/**", "/favicon.ico").permitAll() //STATIC 파일 패싱
                .pathMatchers("/api/authenticate", "/api/authenticateUrl", "/api/authenticate/valid-token").permitAll() //JWT관련 URI
                    .pathMatchers("/api2/**","/cenerp/**","/ifroute/**").permitAll() //현재 게이트웨이서버가 호출하는 인터널API 호출시 토큰없이 패싱
                    .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/api/person").hasRole("USER")
                .pathMatchers("/api/hiddenmessage").hasRole("ADMIN")
                .anyExchange()
                .authenticated());
        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(new BCryptPasswordEncoder());
        return manager;
    }
}
