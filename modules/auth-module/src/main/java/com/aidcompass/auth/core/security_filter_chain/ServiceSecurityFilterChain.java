package com.aidcompass.auth.core.security_filter_chain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ServiceSecurityFilterChain {

    @Order(1)
    @Bean
    public SecurityFilterChain serviceSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/system/**")
                .authenticationManager()
                .httpBasic(basic -> basic
                        .
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/system/v1/specialists/reviews/buffer/batch").hasAnyRole("ADMIN", "SERVICE")
                )
        return http.build();
    }

}
