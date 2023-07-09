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

        // Add additional http security configurations as needed
        http.csrf(csrf -> csrf.disable())
        // Since `addFilterBefore` isn't available, you need to find another way to add the corsFilter.
        // .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling ->
                              exceptionHandling
                                      .authenticationEntryPoint(authenticationErrorHandler)
                                      .accessDeniedHandler(jwtAccessDeniedHandler))
        // session management is stateless by default in webflux
        // .and()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .authorizeExchange(authorizeExchange ->
            authorizeExchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/", "/*.html","/*.css","/*.js","/h2-console/**", "/favicon.ico").permitAll()
                .pathMatchers("/api/authenticate", "/api/authenticateUrl", "/api/authenticate/valid-token").permitAll()
                .pathMatchers("/api/person").hasRole("USER")
                .pathMatchers("/api/hiddenmessage").hasRole("ADMIN")
                .anyExchange()
                .authenticated());

        // apply method is not available in WebFlux, you might want to integrate JWT authentication differently
        // .and()
        // .apply(securityConfigurerAdapter());
        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(new BCryptPasswordEncoder());
        return manager;
    }
}
