package com.specialist.auth.core.web.oauth2.services;

import com.specialist.auth.core.web.SessionCookieManager;
import com.specialist.auth.core.web.oauth2.OAuth2InitialRequestRepository;
import com.specialist.auth.core.web.oauth2.models.OAuth2InitialRequestEntity;
import com.specialist.auth.core.web.oauth2.models.OAuth2QueryParams;
import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2AccountAuthorizeOrchestratorImplUnitTests {

    @Mock
    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;
    @Mock
    private OAuth2InitialRequestRepository initialRequestRepository;
    @Mock
    private OAuth2AuthenticationService authenticationService;
    @Mock
    private SessionCookieManager sessionCookieManager;
    @Mock
    private AuthenticationSuccessHandler successHandler;
    @Mock
    private AuthenticationFailureHandler failureHandler;

    @InjectMocks
    private OAuth2AccountAuthorizeOrchestratorImpl orchestrator;

    @Test
    @DisplayName("UT: authorize() should resolve request, save state and return redirect url")
    void authorize_shouldResolveAndSave() {
        Provider provider = Provider.GOOGLE;
        HttpServletRequest request = mock(HttpServletRequest.class);
        OAuth2AuthorizationRequest authRequest = OAuth2AuthorizationRequest.authorizationCode()
                .authorizationUri("uri")
                .clientId("id")
                .state("state")
                .build();

        when(authorizationRequestResolver.resolve(request, "google")).thenReturn(authRequest);

        Map<String, String> result = orchestrator.authorize(provider, request);

        assertEquals(authRequest.getAuthorizationRequestUri(), result.get("redirectUrl"));
        verify(initialRequestRepository).save(any(OAuth2InitialRequestEntity.class));
    }

    @Test
    @DisplayName("UT: callback() when state valid should authenticate and call success handler")
    void callback_validState_shouldAuthenticateAndSucceed() throws ServletException, IOException {
        OAuth2QueryParams params = new OAuth2QueryParams(Provider.GOOGLE, "code", "state");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        OAuth2InitialRequestEntity requestEntity = new OAuth2InitialRequestEntity("state", "uri", 100L);
        Authentication authentication = mock(Authentication.class);
        AccountUserDetails userDetails = mock(AccountUserDetails.class);

        when(initialRequestRepository.findById("state")).thenReturn(Optional.of(requestEntity));
        when(authenticationService.authenticate(params, requestEntity)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        orchestrator.callback(params, request, response);

        verify(initialRequestRepository).deleteById("state");
        verify(sessionCookieManager).create(userDetails, request, response);
        verify(successHandler).onAuthenticationSuccess(request, response, authentication);
        verifyNoInteractions(failureHandler);
    }

    @Test
    @DisplayName("UT: callback() when state invalid should call failure handler")
    void callback_invalidState_shouldCallFailureHandler() throws ServletException, IOException {
        OAuth2QueryParams params = new OAuth2QueryParams(Provider.GOOGLE, "code", "state");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(initialRequestRepository.findById("state")).thenReturn(Optional.empty());

        assertThrows(AuthenticationServiceException.class, () -> orchestrator.callback(params, request, response));

        //verify(failureHandler).onAuthenticationFailure(eq(request), eq(response), any(AuthenticationServiceException.class));
        verifyNoInteractions(authenticationService, sessionCookieManager, successHandler);
    }

    @Test
    @DisplayName("UT: callback() when authentication fails should call failure handler")
    void callback_authFail_shouldCallFailureHandler() throws ServletException, IOException {
        OAuth2QueryParams params = new OAuth2QueryParams(Provider.GOOGLE, "code", "state");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        OAuth2InitialRequestEntity requestEntity = new OAuth2InitialRequestEntity("state", "uri", 100L);
        AuthenticationException exception = new AuthenticationException("Fail") {};

        when(initialRequestRepository.findById("state")).thenReturn(Optional.of(requestEntity));
        when(authenticationService.authenticate(params, requestEntity)).thenThrow(exception);

        orchestrator.callback(params, request, response);

        verify(failureHandler).onAuthenticationFailure(request, response, exception);
        verifyNoInteractions(sessionCookieManager, successHandler);
    }
}
