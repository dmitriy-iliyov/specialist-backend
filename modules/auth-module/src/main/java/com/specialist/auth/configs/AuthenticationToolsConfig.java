package com.specialist.auth.configs;

import com.specialist.auth.domain.account.services.ExtendedPreAuthenticationChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@Slf4j
public class AuthenticationToolsConfig {

    @Bean("accountAuthenticationManager")
    public AuthenticationManager accountAuthenticationManager(
            @Qualifier("accountUserDetailsService") UserDetailsService accountUserDetailsService,
            PasswordEncoder passwordEncoder,
            @Qualifier("accessTokenUserDetailsService") AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> preAuthenticatedUserDetailsService,
            OAuth2LoginAuthenticationProvider oAuth2LoginAuthenticationProvider
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(accountUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setPreAuthenticationChecks(new ExtendedPreAuthenticationChecker());

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(preAuthenticatedUserDetailsService);

        ProviderManager authenticationManager = new ProviderManager(
                daoAuthenticationProvider,
                preAuthenticatedAuthenticationProvider,
                oAuth2LoginAuthenticationProvider
        );
        authenticationManager.setEraseCredentialsAfterAuthentication(true);
        log.info("AuthenticationManager 'accountAuthenticationManager' configured successfully.");
        return authenticationManager;
    }

    @Bean("serviceAccountAuthenticationManager")
    public AuthenticationManager serviceAccountAuthenticationManager(
            @Qualifier("serviceAccountUserDetailsService") UserDetailsService serviceAccountUserDetailsService,
            PasswordEncoder passwordEncoder,
            @Qualifier("accessTokenUserDetailsService") AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> preAuthenticatedUserDetailsService
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(serviceAccountUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(preAuthenticatedUserDetailsService);

        ProviderManager authenticationManager = new ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider);
        authenticationManager.setEraseCredentialsAfterAuthentication(true);
        log.info("AuthenticationManager 'serviceAccountAuthenticationManager' configured successfully.");
        return authenticationManager;
    }

    @Bean("accountAuthenticationFilter")
    public AuthenticationFilter accountAuthenticationFilter(
            @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
            @Qualifier("cookieAccessTokenAuthenticationConverter") AuthenticationConverter authenticationConverter,
            @Qualifier("accessTokenAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("accessTokenAuthenticationFailureHandler") AuthenticationFailureHandler failureHandler) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, authenticationConverter);
        filter.setSuccessHandler(successHandler);
        filter.setFailureHandler(failureHandler);
        return filter;
    }

    @Bean("serviceAccountAuthenticationFilter")
    public AuthenticationFilter serviceAccountAuthenticationFilter(
            @Qualifier("serviceAccountAuthenticationManager") AuthenticationManager authenticationManager,
            @Qualifier("bearerAccessTokenAuthenticationConverter") AuthenticationConverter authenticationConverter,
            @Qualifier("accessTokenAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("accessTokenAuthenticationFailureHandler") AuthenticationFailureHandler failureHandler) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, authenticationConverter);
        filter.setSuccessHandler(successHandler);
        filter.setFailureHandler(failureHandler);
        return filter;
    }
}
