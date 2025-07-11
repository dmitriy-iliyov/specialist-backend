package com.aidcompass.core.security.configs.security_filter_chain;

import com.aidcompass.core.security.converters.BearerJwtAuthenticationConverter;
import com.aidcompass.core.security.domain.token.serializing.TokenDeserializer;
import com.aidcompass.core.security.handlers.BearerAccessDeniedHandler;
import com.aidcompass.core.security.handlers.BearerAuthenticationEntryPoint;
import com.aidcompass.core.security.handlers.authentication.DefaultAuthenticationFailureHandler;
import com.aidcompass.core.security.handlers.authentication.DefaultAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class ServiceSecurityChainConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter bearerAuthenticationFilter;


    public ServiceSecurityChainConfig(@Qualifier("serviceAuthenticationManager") AuthenticationManager authenticationManager,
                                      TokenDeserializer tokenDeserializer) {
        this.authenticationManager = authenticationManager;
        this.bearerAuthenticationFilter = new AuthenticationFilter(
                authenticationManager,
                new BearerJwtAuthenticationConverter(tokenDeserializer)
        );
        this.bearerAuthenticationFilter.setSuccessHandler(new DefaultAuthenticationSuccessHandler());
        this.bearerAuthenticationFilter.setFailureHandler(new DefaultAuthenticationFailureHandler());
    }


    @Bean
    @Order(1)
    public SecurityFilterChain serviceSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/system/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .addFilterAfter(bearerAuthenticationFilter, CsrfFilter.class)
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers(new IpAddressMatcher("192.168.0.0/16")).permitAll()
                        .requestMatchers("/api/system/v1/auth/login").permitAll()
                        .requestMatchers("/api/system/v1/auth/token").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(
                                "/api/system/v1/intervals/past/batch",
                                "/api/system/v1/appointments/past/batch/skip",
                                "/api/system/v1/appointments/batch/remind"
                        ).hasAuthority("ROLE_SCHEDULE_TASK_SERVICE")
                        .requestMatchers("/api/system/actuator/**").hasAnyAuthority("ROLE_PROMETHEUS_SERVICE", "ROLE_ADMIN")
                        .anyRequest().denyAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new BearerAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerAccessDeniedHandler())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}