package com.aidcompass.auth.core.security_filter_chain;

import com.aidcompass.auth.core.rate_limit.RateLimitFilter;
import com.aidcompass.auth.core.xss.XssFilter;
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
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class AccountSecurityFilterChain {

    private final CorsConfigurationSource corsConfigurationSource;
    private final CsrfTokenRepository csrfTokenRepository;
    private final RateLimitFilter rateLimitFilter;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutHandler refreshTokenLogoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    public AccountSecurityFilterChain(CorsConfigurationSource configurationSource, CsrfTokenRepository csrfTokenRepository,
                                      RateLimitFilter rateLimitFilter, AuthenticationManager authenticationManager,
                                      @Qualifier("accessTokenAuthenticationFilter") AuthenticationFilter authenticationFilter,
                                      @Qualifier("defaultAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
                                      @Qualifier("refreshTokenLogoutHandler") LogoutHandler refreshTokenLogoutHandler,
                                      LogoutSuccessHandler logoutSuccessHandler) {
        this.corsConfigurationSource = configurationSource;
        this.csrfTokenRepository = csrfTokenRepository;
        this.rateLimitFilter = rateLimitFilter;
        this.authenticationManager = authenticationManager;
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.refreshTokenLogoutHandler = refreshTokenLogoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Order(2)
    @Bean
    public SecurityFilterChain accountSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authenticationManager(authenticationManager)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers("/api/csrf", "/api/auth/login", "/api/v1/accounts",
                                                 "/api/v1/accounts/confirmation/**", "/api/v1/accounts/password-recovery/**")
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
                .addFilterAfter(rateLimitFilter, XssFilter.class)
                .addFilterAfter(authenticationFilter, RateLimitFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/info/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/refresh", "/api/auth/logout").authenticated()
                        .requestMatchers("/api/v1/accounts").permitAll()
                        .requestMatchers("/api/v1/accounts/me").hasRole("USER")
                        .requestMatchers("/api/v1/accounts/confirmation/**",
                                         "/api/v1/accounts/password-recovery/**").permitAll()
                        .requestMatchers("/api/v1/users/me", "/api/v1/users/me/avatar").authenticated()
                        .requestMatchers("/api/v1/users", "/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/me/bookmarks", "/api/v1/me/bookmarks/**").authenticated()
                        .requestMatchers("/api/v1/languages").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/specialists/**/reviews").permitAll()
                        .requestMatchers("/api/v1/specialists/**/reviews",
                                         "/api/v1/specialists/**/reviews/**").authenticated()
                        .requestMatchers("/api/v1/me/specialists", "/api/v1/me/specialists/**").hasRole("USER")
                        .requestMatchers("/api/v1/specialists", "/api/v1/specialists/**").permitAll()
                        .requestMatchers("/api/v1/types/**").permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(new CookieClearingLogoutHandler(
                                "__Host-access-token", "__Host-refresh-token", "XSRF-TOKEN"))
                        .addLogoutHandler(refreshTokenLogoutHandler)
                        .logoutSuccessHandler(logoutSuccessHandler)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
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
                );
        return http.build();
    }
}
