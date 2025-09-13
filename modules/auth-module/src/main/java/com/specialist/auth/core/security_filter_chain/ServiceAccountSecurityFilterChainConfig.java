package com.specialist.auth.core.security_filter_chain;

import com.specialist.auth.core.DefaultAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class ServiceAccountSecurityFilterChainConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final DefaultAccessDeniedHandler accessDeniedHandler;

    public ServiceAccountSecurityFilterChainConfig(@Qualifier("serviceAccountAuthenticationManager") AuthenticationManager authenticationManager,
                                                   @Qualifier("serviceAccountAuthenticationFilter") AuthenticationFilter authenticationFilter,
                                                   @Qualifier("bearerAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
                                                   DefaultAccessDeniedHandler accessDeniedHandler) {
        this.authenticationManager = authenticationManager;
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }


    @Order(1)
    @Bean
    public SecurityFilterChain serviceAccountSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/system/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .addFilterAfter(authenticationFilter, CsrfFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/system/v1/auth/login").permitAll()
                        .requestMatchers("/api/system/**").hasAnyRole("ADMIN", "SERVICE")
                        .anyRequest().denyAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

}
