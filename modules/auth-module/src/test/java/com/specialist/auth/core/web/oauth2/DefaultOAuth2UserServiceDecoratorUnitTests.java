package com.specialist.auth.core.web.oauth2;

import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.core.web.oauth2.services.DefaultOAuth2UserServiceDecorator;
import com.specialist.auth.core.web.oauth2.services.OAuth2AccountPersistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestOperations;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultOAuth2UserServiceDecoratorUnitTests {

    @Mock
    private OAuth2AccountPersistService persistService;

    @Mock
    private RestOperations restOperations;

    private DefaultOAuth2UserServiceDecorator userService;

    @BeforeEach
    void setUp() {
        userService = new DefaultOAuth2UserServiceDecorator(persistService);
        userService.setRestOperations(restOperations);
    }

    @Test
    @DisplayName("UT: loadUser should call super, persist user and return DefaultOAuth2User")
    void loadUser_shouldPersistAndReturnUser() {
        String registrationId = "google";
        String userNameAttributeName = "sub";
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId(registrationId)
                .clientId("client-id")
                .clientSecret("client-secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost/login/oauth2/code/google")
                .scope("email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(userNameAttributeName)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(), Instant.now().plusSeconds(3600)
        );

        OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, accessToken);

        Map<String, Object> userAttributes = Map.of("sub", "12345", "email", "test@example.com");
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(userAttributes, HttpStatus.OK);

        when(restOperations.exchange(any(RequestEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        OAuth2User result = userService.loadUser(userRequest);

        assertTrue(result instanceof DefaultOAuth2User);
        assertEquals("12345", result.getName());
        
        verify(persistService, times(1)).saveIfNonExists(eq(Provider.GOOGLE), any(DefaultOAuth2User.class));
    }
}
