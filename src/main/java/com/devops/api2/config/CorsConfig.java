package com.devops.api2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * CORS설정 filter
 * sejin
 */

@Configuration
public class CorsConfig {

   @Bean
   public CorsWebFilter corsWebFilter() {
      CorsConfiguration corsConfig = new CorsConfiguration();
      corsConfig.setAllowCredentials(true);
      corsConfig.addAllowedOrigin("*");
      corsConfig.addAllowedHeader("*");
      corsConfig.addAllowedMethod("*");

      org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source =
              new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/api/**", corsConfig);
      source.registerCorsConfiguration("/api2/**", corsConfig);
      return new CorsWebFilter(source);
   }
}
