package com.specialist.auth.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
public class OAuth2Config {

    @Bean
    public OAuth2LoginAuthenticationProvider oAuth2LoginAuthenticationProvider( OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService) {
        return new OAuth2LoginAuthenticationProvider(new RestClientAuthorizationCodeTokenResponseClient(), oAuth2UserService);
    }

    @Bean
    public OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver(ClientRegistrationRepository repository) {
        return new DefaultOAuth2AuthorizationRequestResolver(repository, "none");
    }
}
