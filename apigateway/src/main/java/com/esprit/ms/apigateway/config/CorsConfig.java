package com.esprit.ms.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow any origin or specific origin
        config.addAllowedOrigin("http://localhost:4200");  // Your Angular app URL
        config.addAllowedOriginPattern("*");  // Also allows any origin (for testing)

        // Allow all HTTP methods
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // Allow specific headers
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("X-Requested-With");

        // Allow credentials (cookies, HTTP authentication, etc.)
        config.setAllowCredentials(true);

        // Register the CORS configuration for all routes
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }


}
