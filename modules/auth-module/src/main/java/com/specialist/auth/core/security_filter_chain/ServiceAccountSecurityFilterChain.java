package com.specialist.auth.core.security_filter_chain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class ServiceAccountSecurityFilterChain {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public ServiceAccountSecurityFilterChain(@Qualifier("serviceAccountAuthenticationManager") AuthenticationManager authenticationManager,
                                             @Qualifier("accountAuthenticationFilter") AuthenticationFilter authenticationFilter,
                                             @Qualifier("bearerAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Order(1)
    @Bean
    public SecurityFilterChain serviceSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/system/**")
                .csrf(csrf -> csrf.disable())
                .authenticationManager(authenticationManager)
                .addFilterAfter(authenticationFilter, CsrfFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/system/v1/auth/login").permitAll()
                        .requestMatchers("/api/system/**").hasAnyRole("ADMIN", "SERVICE")
                        .anyRequest().denyAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

}
