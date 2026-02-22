package com.specialist.auth.core.web.oauth2.services;

import com.specialist.auth.core.web.oauth2.models.OAuth2InitialRequestEntity;
import com.specialist.auth.core.web.oauth2.models.OAuth2QueryParams;
import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.exceptions.InvalidProviderException;
import com.specialist.auth.exceptions.OAuth2UserNullEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2AuthenticationServiceImplUnitTests {

    @Mock
    private ClientRegistrationRepository clientRegistrationRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private OAuth2AuthenticationServiceImpl service;

    @Test
    @DisplayName("UT: authenticate() should authenticate and return PreAuthenticatedToken")
    void authenticate_shouldAuthenticate() {
        OAuth2QueryParams params = new OAuth2QueryParams(Provider.GOOGLE, "code", "state");
        OAuth2InitialRequestEntity initialRequest = new OAuth2InitialRequestEntity("state", "uri", 300L);
        
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("uri")
                .redirectUri("uri")
                .tokenUri("uri")
                .build();

        Authentication oAuth2Authentication = mock(Authentication.class);
        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), Map.of("email", "test@test.com"), "email");
        AccountUserDetails userDetails = mock(AccountUserDetails.class);

        when(clientRegistrationRepository.findByRegistrationId("google")).thenReturn(clientRegistration);
        when(authenticationManager.authenticate(any(OAuth2LoginAuthenticationToken.class))).thenReturn(oAuth2Authentication);
        when(oAuth2Authentication.getPrincipal()).thenReturn(oAuth2User);
        when(userDetailsService.loadUserByUsername("test@test.com")).thenReturn(userDetails);
        when(userDetails.getProvider()).thenReturn(Provider.GOOGLE);

        Authentication result = service.authenticate(params, initialRequest);

        assertNotNull(result);
        assertTrue(result instanceof PreAuthenticatedAuthenticationToken);
        assertEquals(userDetails, result.getPrincipal());
        
        verify(clientRegistrationRepository).findByRegistrationId("google");
        verify(authenticationManager).authenticate(any(OAuth2LoginAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername("test@test.com");
    }

    @Test
    @DisplayName("UT: authenticate() when email is null should throw exception")
    void authenticate_whenEmailNull_shouldThrowException() {
        OAuth2QueryParams params = new OAuth2QueryParams(Provider.GOOGLE, "code", "state");
        OAuth2InitialRequestEntity initialRequest = new OAuth2InitialRequestEntity("state", "uri", 300L);
        
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("uri")
                .redirectUri("uri")
                .tokenUri("uri")
                .build();

        Authentication oAuth2Authentication = mock(Authentication.class);
        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), Map.of("sub", "123"), "sub");

        when(clientRegistrationRepository.findByRegistrationId("google")).thenReturn(clientRegistration);
        when(authenticationManager.authenticate(any(OAuth2LoginAuthenticationToken.class))).thenReturn(oAuth2Authentication);
        when(oAuth2Authentication.getPrincipal()).thenReturn(oAuth2User);

        assertThrows(OAuth2UserNullEmailException.class, () -> service.authenticate(params, initialRequest));
    }

    @Test
    @DisplayName("UT: authenticate() when provider is LOCAL should throw exception")
    void authenticate_whenProviderLocal_shouldThrowException() {
        OAuth2QueryParams params = new OAuth2QueryParams(Provider.GOOGLE, "code", "state");
        OAuth2InitialRequestEntity initialRequest = new OAuth2InitialRequestEntity("state", "uri", 300L);
        
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("uri")
                .redirectUri("uri")
                .tokenUri("uri")
                .build();

        Authentication oAuth2Authentication = mock(Authentication.class);
        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(Collections.emptyList(), Map.of("email", "test@test.com"), "email");
        AccountUserDetails userDetails = mock(AccountUserDetails.class);

        when(clientRegistrationRepository.findByRegistrationId("google")).thenReturn(clientRegistration);
        when(authenticationManager.authenticate(any(OAuth2LoginAuthenticationToken.class))).thenReturn(oAuth2Authentication);
        when(oAuth2Authentication.getPrincipal()).thenReturn(oAuth2User);
        when(userDetailsService.loadUserByUsername("test@test.com")).thenReturn(userDetails);
        when(userDetails.getProvider()).thenReturn(Provider.LOCAL);

        assertThrows(InvalidProviderException.class, () -> service.authenticate(params, initialRequest));
    }
}
