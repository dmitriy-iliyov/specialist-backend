package com.specialist.auth.core.security_filter_chain;

import com.specialist.auth.core.DefaultAccessDeniedHandler;
import com.specialist.auth.core.rate_limit.RateLimitFilter;
import com.specialist.auth.core.web.xss.XssFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class AccountSecurityFilterChainConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final CsrfTokenRepository csrfTokenRepository;
    private final RateLimitFilter rateLimitFilter;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final DefaultAccessDeniedHandler accessDeniedHandler;

    public AccountSecurityFilterChainConfig(CorsConfigurationSource configurationSource, CsrfTokenRepository csrfTokenRepository,
                                            RateLimitFilter rateLimitFilter,
                                            @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
                                            @Qualifier("accountAuthenticationFilter") AuthenticationFilter authenticationFilter,
                                            @Qualifier("cookieAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
                                            DefaultAccessDeniedHandler accessDeniedHandler) {
        this.corsConfigurationSource = configurationSource;
        this.csrfTokenRepository = csrfTokenRepository;
        this.rateLimitFilter = rateLimitFilter;
        this.authenticationManager = authenticationManager;
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Order(2)
    @Bean
    public SecurityFilterChain accountSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")
                .authenticationManager(authenticationManager)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers("/api/v1/accounts", "/api/csrf", "/api/auth/login",
                                                 "/api/auth/oauth2/authorize", "/api/auth/oauth2/callback",
                                                 "/api/v1/accounts/confirmation/**",
                                                 "/api/v1/accounts/password-recovery/**", "/api/auth/refresh")
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
                .addFilterAfter(rateLimitFilter, XssFilter.class)
                .addFilterAfter(authenticationFilter, rateLimitFilter.getClass())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/csrf").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/info/**").permitAll()
                        .requestMatchers("/api/auth/login", "/api/auth/oauth2/authorize",
                                         "/api/auth/oauth2/callback").permitAll()
                        .requestMatchers("/api/auth/refresh", "/api/auth/logout", "/api/auth/logout/all").authenticated()
                        .requestMatchers("/api/v1/accounts").permitAll()
                        .requestMatchers("/api/v1/accounts/me").hasAnyRole("USER", "SPECIALIST")
                        .requestMatchers("/api/v1/accounts/confirmation/**",
                                         "/api/v1/accounts/password-recovery/**").permitAll()
                        .requestMatchers("/api/v1/profiles/me", "/api/v1/profiles/me/avatar").authenticated()
                        .requestMatchers("/api/v1/profiles", "/api/v1/profiles/**").permitAll()
                        .requestMatchers("/api/v1/me/bookmarks", "/api/v1/me/bookmarks/**").authenticated()
                        .requestMatchers("/api/v1/languages", "/api/v1/languages/**", "/api/v1/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/specialists/{specialist_id}/reviews").permitAll()
                        .requestMatchers("/api/v1/specialists/{specialist_id}/reviews",
                                         "/api/v1/specialists/{specialist_id}/reviews/**",
                                         "/api/v1/specialists/{id}/manage", "/api/v1/specialists/manage").authenticated()
                        .requestMatchers("/api/v1/me/specialists", "/api/v1/me/specialists/**").hasRole("USER")
                        .requestMatchers("/api/v1/specialists", "/api/v1/specialists/**").permitAll()
                        .requestMatchers("/api/v1/types/**").permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers -> headers
                                .xssProtection(xss -> xss.disable())
                                .contentSecurityPolicy(
                                        cps -> cps.policyDirectives("script-src 'self'; frame-ancestors 'none';")
                                )
                                .frameOptions(xfo -> xfo.deny())
                                .contentTypeOptions(Customizer.withDefaults()) //nosniff
                                .httpStrictTransportSecurity(
                                        sts -> sts.maxAgeInSeconds(31536000).includeSubDomains(true)
                                )
                )
                .build();
    }
}
