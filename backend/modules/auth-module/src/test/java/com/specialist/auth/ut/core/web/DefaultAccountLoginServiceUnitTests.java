package com.specialist.auth.ut.core.web;

import com.specialist.auth.core.Token;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.core.web.DefaultAccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import com.specialist.auth.core.web.SessionCookieManager;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.InvalidProviderException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAccountLoginServiceUnitTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountService accountService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private AccountUserDetails userDetails;

    @Mock
    private SessionCookieManager sessionCookieManager;

    private DefaultAccountLoginService orchestrator;

    @BeforeEach
    void setUp() {
        orchestrator = new DefaultAccountLoginService(
                authenticationManager,
                accountService,
                sessionCookieManager
        );
    }

    @Test
    @DisplayName("UT: login() with LOCAL provider should successfully authenticate")
    void login_localProvider_shouldAuthenticateSuccessfully() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        String accessTokenValue = "access-token-123";
        String refreshTokenValue = "refresh-token-123";
        Instant expiresAt = Instant.now().plusSeconds(3600);

        Token accessToken = new Token(TokenType.ACCESS, accessTokenValue, expiresAt);
        Token refreshToken = new Token(TokenType.REFRESH, refreshTokenValue, expiresAt);
        Map<TokenType, Token> tokens = Map.of(
                TokenType.ACCESS, accessToken,
                TokenType.REFRESH, refreshToken
        );

        when(accountService.findProviderByEmail(loginRequest.email())).thenReturn(Provider.LOCAL);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {

            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            orchestrator.login(loginRequest, request, response);

            verify(accountService).findProviderByEmail(loginRequest.email());
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(securityContext).setAuthentication(authentication);
            verify(authentication).getPrincipal();
            verifyNoMoreInteractions(accountService, authenticationManager, securityContext, authentication, response);
        }
    }

    @Test
    @DisplayName("UT: login() with non-LOCAL provider should throw OAuth2RegisteredAttemptedToLocalLoginException")
    void login_nonLocalProvider_shouldThrowOAuth2Exception() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        when(accountService.findProviderByEmail(loginRequest.email())).thenReturn(Provider.GOOGLE);

        assertThrows(InvalidProviderException.class,
                () -> orchestrator.login(loginRequest, request, response));

        verify(accountService).findProviderByEmail(loginRequest.email());
        verifyNoMoreInteractions(accountService);
    }

    @Test
    @DisplayName("UT: login() with authentication failure should clear security context and rethrow exception")
    void login_authenticationFailure_shouldClearContextAndRethrowException() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrongpassword");
        AuthenticationException authException = new BadCredentialsException("Invalid credentials");

        when(accountService.findProviderByEmail(loginRequest.email())).thenReturn(Provider.LOCAL);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(authException);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {

            AuthenticationException thrownException = assertThrows(AuthenticationException.class,
                    () -> orchestrator.login(loginRequest, request, response));

            assertEquals(authException, thrownException);
            verify(accountService).findProviderByEmail(loginRequest.email());
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            securityContextHolderMock.verify(SecurityContextHolder::clearContext);
            verifyNoInteractions(response);
            verifyNoMoreInteractions(accountService, authenticationManager);
        }
    }

    @Test
    @DisplayName("UT: login() should create correct authentication token with email and password")
    void login_validCredentials_shouldCreateCorrectAuthenticationToken() {
        LoginRequest loginRequest = new LoginRequest("user@test.com", "mypassword");

        when(accountService.findProviderByEmail(loginRequest.email())).thenReturn(Provider.LOCAL);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            orchestrator.login(loginRequest, request, response);

            verify(authenticationManager).authenticate(argThat(token ->
                    token instanceof UsernamePasswordAuthenticationToken &&
                            "user@test.com".equals(token.getPrincipal()) &&
                            "mypassword".equals(token.getCredentials())
            ));
            verifyNoMoreInteractions(authenticationManager);
        }
    }
}