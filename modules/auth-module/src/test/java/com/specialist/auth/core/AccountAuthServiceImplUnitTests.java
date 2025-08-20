package com.specialist.auth.core;

import com.specialist.auth.core.csrf.CsrfTokenService;
import com.specialist.auth.core.models.LoginRequest;
import com.specialist.auth.core.models.Token;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.OAuth2RegisteredAttemptedToLocalLoginException;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.http.Cookie;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountAuthServiceImplUnitTests {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountService accountService;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private CsrfTokenService csrfTokenService;

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

    private AccountAuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AccountAuthServiceImpl(
                userDetailsService,
                authenticationManager,
                accountService,
                tokenManager,
                csrfTokenService
        );
    }

    @Test
    @DisplayName("UT: postConfirmationLogin() should successfully authenticate user and set tokens")
    void postConfirmationLogin_validEmail_shouldAuthenticateAndSetTokens() {
        String email = "test@example.com";
        String accessTokenValue = "access-token-123";
        String refreshTokenValue = "refresh-token-123";
        Instant expiresAt = Instant.now().plusSeconds(3600);

        Token accessToken = new Token(TokenType.ACCESS, accessTokenValue, expiresAt);
        Token refreshToken = new Token(TokenType.REFRESH, refreshTokenValue, expiresAt);
        Map<TokenType, Token> tokens = Map.of(
                TokenType.ACCESS, accessToken,
                TokenType.REFRESH, refreshToken
        );

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(userDetails.getAuthorities()).thenReturn((Collection) List.of(new SimpleGrantedAuthority("ROLE_USER")));
        when(tokenManager.generate(userDetails)).thenReturn(tokens);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class);
             MockedStatic<AuthCookieFactory> cookieFactoryMock = mockStatic(AuthCookieFactory.class)) {

            Cookie accessCookie = new Cookie("__Host-access-token", accessTokenValue);
            Cookie refreshCookie = new Cookie("__Host-refresh-token", refreshTokenValue);

            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            cookieFactoryMock.when(() -> AuthCookieFactory.generate(accessTokenValue, expiresAt, TokenType.ACCESS))
                    .thenReturn(accessCookie);
            cookieFactoryMock.when(() -> AuthCookieFactory.generate(refreshTokenValue, expiresAt, TokenType.REFRESH))
                    .thenReturn(refreshCookie);

            authService.postConfirmationLogin(email, request, response);

            verify(userDetailsService).loadUserByUsername(email);
            verify(securityContext).setAuthentication(any(UsernamePasswordAuthenticationToken.class));
            verify(tokenManager).generate(userDetails);
            verify(response).addCookie(accessCookie);
            verify(response).addCookie(refreshCookie);
            verify(csrfTokenService).onAuthentication(request, response);
            verifyNoMoreInteractions(userDetailsService, securityContext, tokenManager, response, csrfTokenService);
        }
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
        when(tokenManager.generate(userDetails)).thenReturn(tokens);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class);
             MockedStatic<AuthCookieFactory> cookieFactoryMock = mockStatic(AuthCookieFactory.class)) {

            Cookie accessCookie = new Cookie("__Host-access-token", accessTokenValue);
            Cookie refreshCookie = new Cookie("__Host-refresh-token", refreshTokenValue);

            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            cookieFactoryMock.when(() -> AuthCookieFactory.generate(accessTokenValue, expiresAt, TokenType.ACCESS))
                    .thenReturn(accessCookie);
            cookieFactoryMock.when(() -> AuthCookieFactory.generate(refreshTokenValue, expiresAt, TokenType.REFRESH))
                    .thenReturn(refreshCookie);

            authService.login(loginRequest, request, response);

            verify(accountService).findProviderByEmail(loginRequest.email());
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(securityContext).setAuthentication(authentication);
            verify(authentication).getPrincipal();
            verify(tokenManager).generate(userDetails);
            verify(response).addCookie(accessCookie);
            verify(response).addCookie(refreshCookie);
            verify(csrfTokenService).onAuthentication(request, response);
            verifyNoMoreInteractions(accountService, authenticationManager, securityContext, authentication, tokenManager, response, csrfTokenService);
        }
    }

    @Test
    @DisplayName("UT: login() with non-LOCAL provider should throw OAuth2RegisteredAttemptedToLocalLoginException")
    void login_nonLocalProvider_shouldThrowOAuth2Exception() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        when(accountService.findProviderByEmail(loginRequest.email())).thenReturn(Provider.GOOGLE);

        assertThrows(OAuth2RegisteredAttemptedToLocalLoginException.class,
                () -> authService.login(loginRequest, request, response));

        verify(accountService).findProviderByEmail(loginRequest.email());
        verifyNoInteractions(authenticationManager, tokenManager, response, csrfTokenService);
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
                    () -> authService.login(loginRequest, request, response));

            assertEquals(authException, thrownException);
            verify(accountService).findProviderByEmail(loginRequest.email());
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            securityContextHolderMock.verify(SecurityContextHolder::clearContext);
            verifyNoInteractions(tokenManager, response, csrfTokenService);
            verifyNoMoreInteractions(accountService, authenticationManager);
        }
    }

    @Test
    @DisplayName("UT: refresh() with valid token should generate new access token")
    void refresh_validToken_shouldGenerateNewAccessToken() {
        UUID refreshTokenId = UUID.randomUUID();
        String newAccessTokenValue = "new-access-token-123";
        Instant expiresAt = Instant.now().plusSeconds(3600);
        Token newAccessToken = new Token(TokenType.ACCESS, newAccessTokenValue, expiresAt);

        when(tokenManager.refresh(refreshTokenId)).thenReturn(newAccessToken);

        try (MockedStatic<AuthCookieFactory> cookieFactoryMock = mockStatic(AuthCookieFactory.class)) {
            Cookie accessCookie = new Cookie("__Host-access-token", newAccessTokenValue);
            cookieFactoryMock.when(() -> AuthCookieFactory.generate(newAccessTokenValue, expiresAt, TokenType.ACCESS))
                    .thenReturn(accessCookie);

            authService.refresh(refreshTokenId, response);

            verify(tokenManager).refresh(refreshTokenId);
            verify(response).addCookie(accessCookie);
            verifyNoMoreInteractions(tokenManager, response);
        }
    }

    @Test
    @DisplayName("UT: refresh() with expired token should clear cookies and rethrow exception")
    void refresh_expiredToken_shouldClearCookiesAndRethrowException() {
        UUID refreshTokenId = UUID.randomUUID();
        RefreshTokenExpiredException expiredException = new RefreshTokenExpiredException();

        when(tokenManager.refresh(refreshTokenId)).thenThrow(expiredException);

        try (MockedStatic<AuthCookieFactory> cookieFactoryMock = mockStatic(AuthCookieFactory.class)) {
            Cookie emptyRefreshCookie = new Cookie("__Host-refresh-token", "");
            Cookie emptyAccessCookie = new Cookie("__Host-access-token", "");

            cookieFactoryMock.when(() -> AuthCookieFactory.generateEmpty(TokenType.REFRESH))
                    .thenReturn(emptyRefreshCookie);
            cookieFactoryMock.when(() -> AuthCookieFactory.generateEmpty(TokenType.ACCESS))
                    .thenReturn(emptyAccessCookie);

            RefreshTokenExpiredException thrownException = assertThrows(RefreshTokenExpiredException.class,
                    () -> authService.refresh(refreshTokenId, response));

            assertEquals(expiredException, thrownException);
            verify(tokenManager).refresh(refreshTokenId);
            verify(response).addCookie(emptyRefreshCookie);
            verify(response).addCookie(emptyAccessCookie);
            verifyNoMoreInteractions(tokenManager, response);
        }
    }

    @Test
    @DisplayName("UT: postConfirmationLogin() should handle single token type correctly")
    void postConfirmationLogin_singleTokenType_shouldHandleCorrectly() {
        String email = "test@example.com";
        String accessTokenValue = "access-token-only";
        Instant expiresAt = Instant.now().plusSeconds(1800);

        Token accessToken = new Token(TokenType.ACCESS, accessTokenValue, expiresAt);
        Map<TokenType, Token> tokens = Map.of(TokenType.ACCESS, accessToken);

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
        when(tokenManager.generate(userDetails)).thenReturn(tokens);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class);
             MockedStatic<AuthCookieFactory> cookieFactoryMock = mockStatic(AuthCookieFactory.class)) {

            Cookie accessCookie = new Cookie("__Host-access-token", accessTokenValue);

            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            cookieFactoryMock.when(() -> AuthCookieFactory.generate(accessTokenValue, expiresAt, TokenType.ACCESS))
                    .thenReturn(accessCookie);

            authService.postConfirmationLogin(email, request, response);

            verify(userDetailsService).loadUserByUsername(email);
            verify(securityContext).setAuthentication(any(UsernamePasswordAuthenticationToken.class));
            verify(tokenManager).generate(userDetails);
            verify(response).addCookie(accessCookie);
            verify(response, times(1)).addCookie(any(Cookie.class)); // Only one cookie added
            verify(csrfTokenService).onAuthentication(request, response);
            verifyNoMoreInteractions(userDetailsService, securityContext, tokenManager, response, csrfTokenService);
        }
    }

    @Test
    @DisplayName("UT: login() should create correct authentication token with email and password")
    void login_validCredentials_shouldCreateCorrectAuthenticationToken() {
        LoginRequest loginRequest = new LoginRequest("user@test.com", "mypassword");

        when(accountService.findProviderByEmail(loginRequest.email())).thenReturn(Provider.LOCAL);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(tokenManager.generate(userDetails)).thenReturn(Map.of());

        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            authService.login(loginRequest, request, response);

            verify(authenticationManager).authenticate(argThat(token ->
                    token instanceof UsernamePasswordAuthenticationToken &&
                            "user@test.com".equals(token.getPrincipal()) &&
                            "mypassword".equals(token.getCredentials())
            ));
            verifyNoMoreInteractions(authenticationManager);
        }
    }
}