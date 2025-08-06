package com.aidcompass.auth.core.configs;

import com.aidcompass.auth.domain.access_token.AccessTokenUserDetailsService;
import com.aidcompass.auth.domain.refresh_token.RefreshTokenService;
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
public class AuthenticationToolsConfig {


    @Primary
    @Bean
    public AuthenticationManager authenticationManager(
            @Qualifier("unifiedAccountService") UserDetailsService accountUserDetailsService,
            PasswordEncoder passwordEncoder,
            RefreshTokenService service) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new AccessTokenUserDetailsService(service));

        ProviderManager authenticationManager = new ProviderManager(daoAuthenticationProvider, preAuthenticatedAuthenticationProvider);
        authenticationManager.setEraseCredentialsAfterAuthentication(true);
        return authenticationManager;
    }
}
