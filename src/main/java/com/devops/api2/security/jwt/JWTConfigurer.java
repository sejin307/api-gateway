package com.devops.api2.security.jwt;

import com.devops.api2.security.JwtAccessDeniedHandler;
import com.devops.api2.security.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class JWTConfigurer {

    private TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public JWTConfigurer(TokenProvider tokenProvider,
                         JwtAuthenticationEntryPoint authenticationErrorHandler,
                         JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        JWTFilter jwtFilter = new JWTFilter(tokenProvider);

        http.addFilterAt(jwtFilter, SecurityWebFiltersOrder.HTTP_BASIC);

        // Add additional http security configurations as needed
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
        .pathMatchers("/api/hiddenmessage").hasRole("ADMIN") // 관리자권한만 접근
        .anyExchange()
        .authenticated();

        // apply method is not available in WebFlux, you might want to integrate JWT authentication differently
        // .and()
        // .apply(securityConfigurerAdapter());
        return http.build();
    }
}
