package com.aidcompass.auth.domain.access_token.config;

import com.aidcompass.auth.domain.access_token.AccessTokenAuthenticationConverter;
import com.aidcompass.auth.domain.access_token.AccessTokenAuthenticationFailureHandler;
import com.aidcompass.auth.domain.access_token.AccessTokenAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class AccessTokenAuthenticationFilterConfig {

    @Bean
    public AuthenticationFilter accessTokenAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                AccessTokenAuthenticationConverter authenticationConverter,
                                                                AccessTokenAuthenticationSuccessHandler successHandler,
                                                                AccessTokenAuthenticationFailureHandler failureHandler) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, authenticationConverter);
        filter.setSuccessHandler(successHandler);
        filter.setFailureHandler(failureHandler);
        return filter;
    }
}
