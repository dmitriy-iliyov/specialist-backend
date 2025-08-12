package com.specialist.auth.core.security_filter_chain;

import com.specialist.auth.core.rate_limit.RateLimitFilter;
import com.specialist.auth.core.xss.XssFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    private final AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutHandler refreshTokenLogoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    public static final String OAUTH2_URL_TEMPLATE = "/api/auth/oauth2/authorization/%s";

    public AccountSecurityFilterChain(CorsConfigurationSource configurationSource, CsrfTokenRepository csrfTokenRepository,
                                      RateLimitFilter rateLimitFilter,
                                      @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
                                      @Qualifier("accountAuthenticationFilter") AuthenticationFilter authenticationFilter,
                                      @Qualifier("defaultOAuth2UserServiceDecorator")
                                      OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService,
                                      @Qualifier("oAuth2AuthenticationSuccessHandler") AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                      @Qualifier("cookieAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
                                      @Qualifier("refreshTokenLogoutHandler") LogoutHandler refreshTokenLogoutHandler,
                                      LogoutSuccessHandler logoutSuccessHandler) {
        this.corsConfigurationSource = configurationSource;
        this.csrfTokenRepository = csrfTokenRepository;
        this.rateLimitFilter = rateLimitFilter;
        this.authenticationManager = authenticationManager;
        this.authenticationFilter = authenticationFilter;
        this.oAuth2UserService = oAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.refreshTokenLogoutHandler = refreshTokenLogoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
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
                        .ignoringRequestMatchers("/api/csrf", "/api/auth/login", OAUTH2_URL_TEMPLATE.formatted("**"),
                                                 "/api/v1/accounts", "/api/v1/accounts/confirmation/**",
                                                 "/api/v1/accounts/password-recovery/**")
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
                .addFilterAfter(rateLimitFilter, XssFilter.class)
                .addFilterAfter(authenticationFilter, RateLimitFilter.class)
                .oauth2Login(login -> login
                        .loginPage(OAUTH2_URL_TEMPLATE.formatted("{registrationId}"))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .userInfoEndpoint(infoEndpoint -> infoEndpoint.userService(oAuth2UserService))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/info/**").permitAll()
                        .requestMatchers("/api/auth/login", OAUTH2_URL_TEMPLATE.formatted("**")).permitAll()
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
                )
                .build();
    }
}
