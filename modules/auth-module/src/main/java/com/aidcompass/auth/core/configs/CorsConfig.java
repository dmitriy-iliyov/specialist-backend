package com.aidcompass.auth.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration globalConfig = new CorsConfiguration();
        globalConfig.addAllowedOrigin("http://localhost:4200");
        globalConfig.setAllowCredentials(true);
        globalConfig.setAllowedMethods(List.of("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        globalConfig.setAllowedHeaders(List.of("*"));
        globalConfig.setMaxAge(86400L);

        CorsConfiguration csrfConfig = new CorsConfiguration();
        csrfConfig.addAllowedOrigin("http://localhost:4200");
        csrfConfig.setAllowCredentials(true);
        csrfConfig.setAllowedMethods(List.of("OPTIONS", "GET"));
        csrfConfig.setAllowedHeaders(List.of("*"));
        csrfConfig.setMaxAge(86400L);

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/api/csrf", csrfConfig);
        configurationSource.registerCorsConfiguration("/**", globalConfig);
        return configurationSource;
    }
}
