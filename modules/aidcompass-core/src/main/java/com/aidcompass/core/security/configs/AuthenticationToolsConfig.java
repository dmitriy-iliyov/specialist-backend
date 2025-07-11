package com.aidcompass.core.security.configs;

import com.aidcompass.core.security.domain.token.TokenUserDetailsService;
import com.aidcompass.core.security.handlers.CookieAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;


@Configuration
@Log4j2
public class AuthenticationToolsConfig {

    private final UserDetailsService userDetailsService;
    private final UserDetailsService serviceUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenUserDetailsService tokenUserDetailsService;
    private final ObjectMapper mapper;


    public AuthenticationToolsConfig(@Qualifier("unifiedUserService") UserDetailsService userDetailsService,
                                     @Qualifier("serviceUserDetailsService") UserDetailsService serviceUserDetailsService,
                                     PasswordEncoder passwordEncoder,
                                     TokenUserDetailsService tokenUserDetailsService,
                                     ObjectMapper mapper) {
        this.userDetailsService = userDetailsService;
        this.serviceUserDetailsService = serviceUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUserDetailsService = tokenUserDetailsService;
        this.mapper = mapper;
    }

    @Bean(name = "userAuthenticationManager")
    @Primary
    public AuthenticationManager userAuthenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(tokenUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider);
    }

    @Bean(name = "serviceAuthenticationManager")
    public AuthenticationManager serviceAuthenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(serviceUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(tokenUserDetailsService);

        return new ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider);
    }

    @Bean
    public CookieAuthenticationEntryPoint cookieAuthenticationEntryPoint() {
        return new CookieAuthenticationEntryPoint(mapper);
    }
}