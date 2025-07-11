package com.aidcompass.core.security.configs.security_filter_chain;


import com.aidcompass.core.security.converters.CookieJwtAuthenticationConverter;
import com.aidcompass.core.security.domain.token.TokenUserDetailsService;
import com.aidcompass.core.security.domain.token.serializing.TokenDeserializer;
import com.aidcompass.core.security.handlers.CookieAuthenticationEntryPoint;
import com.aidcompass.core.security.handlers.CsrfAccessDeniedHandler;
import com.aidcompass.core.security.handlers.authentication.DefaultAuthenticationFailureHandler;
import com.aidcompass.core.security.handlers.authentication.DefaultAuthenticationSuccessHandler;
import com.aidcompass.core.security.handlers.logout.DeactivatingTokenLogoutHandler;
import com.aidcompass.core.security.handlers.logout.LogoutSuccessHandlerImpl;
import com.aidcompass.core.security.xss.XssFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class UserSecurityChainConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final CsrfTokenRepository csrfTokenRepository;
    private final CsrfAccessDeniedHandler csrfAccessDeniedHandler;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFilter cookieAuthenticationFilter;
    private final TokenUserDetailsService tokenUserDetailsService;
    private final CookieAuthenticationEntryPoint authenticationEntryPoint;


    public UserSecurityChainConfig(CorsConfigurationSource corsConfigurationSource,
                                   CsrfTokenRepository csrfTokenRepository,
                                   CsrfAccessDeniedHandler csrfAccessDeniedHandler,
                                   @Qualifier("userAuthenticationManager") AuthenticationManager authenticationManager,
                                   TokenUserDetailsService tokenUserDetailsService,
                                   CookieAuthenticationEntryPoint authenticationEntryPoint,
                                   TokenDeserializer tokenDeserializer
    ) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.csrfTokenRepository = csrfTokenRepository;
        this.csrfAccessDeniedHandler = csrfAccessDeniedHandler;
        this.authenticationManager = authenticationManager;
        this.cookieAuthenticationFilter = new AuthenticationFilter(
                authenticationManager,
                new CookieJwtAuthenticationConverter(tokenDeserializer)
        );
        this.cookieAuthenticationFilter.setSuccessHandler(new DefaultAuthenticationSuccessHandler());
        this.cookieAuthenticationFilter.setFailureHandler(new DefaultAuthenticationFailureHandler());
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.tokenUserDetailsService = tokenUserDetailsService;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers("/api/auth/login", "/api/users", "/api/v1/contact",
                                                           "/api/v1/contacts", "/api/confirmations/linked-email/request",
                                                           "/api/confirmations/linked-email", "/api/csrf")
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
                .authenticationManager(authenticationManager)
                .addFilterAfter(cookieAuthenticationFilter, XssFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(new CookieClearingLogoutHandler(
                                "__Host-auth_token", "XSRF-TOKEN", "auth_info")
                        )
                        .addLogoutHandler(new DeactivatingTokenLogoutHandler(tokenUserDetailsService))
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/logout", "/api/auth/me").authenticated()
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/api/confirmations/resource", "/api/confirmations/request").authenticated()
                        .requestMatchers("/api/confirmations/linked-email/request",
                                         "/api/confirmations/linked-email").permitAll()
                        .requestMatchers("/api/info/**").permitAll()
                        .requestMatchers("/api/aggregator/doctors/me", "/api/aggregator/doctors/me/**",
                                         "/api/aggregator/jurists/me", "/api/aggregator/jurists/me/**",
                                         "/api/aggregator/customers/**").authenticated()
                        .requestMatchers("/api/aggregator/**").permitAll()
                        .requestMatchers("/api/v1/appointments/**", "/api/v1/appointments/duration/me",
                                         "/api/v1/days/me", "/api/v1/timetable/me/**").authenticated()
                        .requestMatchers("/api/v1/appointments/duration/**", "/api/v1/intervals/nearest/**",
                                         "/api/v1/days/**", "/api/v1/timetable/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/avatars/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/specialists").hasAuthority("ROLE_CUSTOMER")
                        .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(csrfAccessDeniedHandler)
                )
                .headers(headers -> headers
                        .httpStrictTransportSecurity(
                                sts -> sts.maxAgeInSeconds(31536000).includeSubDomains(true)
                        )
                        .xssProtection(xss -> xss.disable())
                        .contentSecurityPolicy(
                                cps -> cps.policyDirectives("script-src 'self'; frame-ancestors 'none';")
                        )
                        .contentTypeOptions(Customizer.withDefaults()) //nosniff
                        .frameOptions(xfo -> xfo.deny())
                );
        return http.build();
    }
}