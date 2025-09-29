package com.specialist.auth.configs;

import com.specialist.auth.domain.access_token.AccessTokenAuthenticationFailureHandler;
import com.specialist.auth.domain.access_token.AccessTokenAuthenticationSuccessHandler;
import com.specialist.auth.domain.access_token.AccessTokenUserDetailsService;
import com.specialist.auth.domain.account.services.ExtendedPreAuthenticationChecker;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
@Slf4j
public class AuthenticationToolsConfig {

    @Bean("accountAuthenticationManager")
    public AuthenticationManager accountAuthenticationManager(
            @Qualifier("accountUserDetailsService") UserDetailsService accountUserDetailsService,
            PasswordEncoder passwordEncoder, RefreshTokenService service,
            OAuth2LoginAuthenticationProvider oAuth2LoginAuthenticationProvider) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setPreAuthenticationChecks(new ExtendedPreAuthenticationChecker());

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new AccessTokenUserDetailsService(service));

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
            RefreshTokenService service) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(serviceAccountUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new AccessTokenUserDetailsService(service));

        ProviderManager authenticationManager = new ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider);
        authenticationManager.setEraseCredentialsAfterAuthentication(true);
        log.info("AuthenticationManager 'serviceAccountAuthenticationManager' configured successfully.");
        return authenticationManager;
    }

    @Bean("accountAuthenticationFilter")
    public AuthenticationFilter accountAuthenticationFilter(
            @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
            @Qualifier("cookieAccessTokenAuthenticationConverter") AuthenticationConverter authenticationConverter,
            AccessTokenAuthenticationSuccessHandler successHandler, AccessTokenAuthenticationFailureHandler failureHandler) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, authenticationConverter);
        filter.setSuccessHandler(successHandler);
        filter.setFailureHandler(failureHandler);
        return filter;
    }

    @Bean("serviceAccountAuthenticationFilter")
    public AuthenticationFilter serviceAccountAuthenticationFilter(
            @Qualifier("serviceAccountAuthenticationManager") AuthenticationManager authenticationManager,
            @Qualifier("bearerAccessTokenAuthenticationConverter") AuthenticationConverter authenticationConverter,
            AccessTokenAuthenticationSuccessHandler successHandler, AccessTokenAuthenticationFailureHandler failureHandler) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, authenticationConverter);
        filter.setSuccessHandler(successHandler);
        filter.setFailureHandler(failureHandler);
        return filter;
    }
}
