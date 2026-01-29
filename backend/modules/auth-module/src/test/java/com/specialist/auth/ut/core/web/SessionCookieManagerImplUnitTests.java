package com.specialist.auth.ut.core.web;

import com.specialist.auth.core.Token;
import com.specialist.auth.core.TokenManager;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.web.CookieManager;
import com.specialist.auth.core.web.CookieType;
import com.specialist.auth.core.web.SessionCookieManagerImpl;
import com.specialist.auth.core.web.csrf.CsrfTokenService;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionCookieManagerImplUnitTests {

    @Mock
    private TokenManager tokenManager;

    @Mock
    private CsrfTokenService csrfTokenService;

    @Mock
    private CookieManager cookieManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private SessionCookieManagerImpl sessionCookieManager;

    @Test
    @DisplayName("UT: create() should generate tokens and add cookies")
    void create_shouldGenerateTokensAndAddCookies() {
        AccountUserDetails userDetails = mock(AccountUserDetails.class);
        Token accessToken = new Token(TokenType.ACCESS, "access", Instant.now());
        Token refreshToken = new Token(TokenType.REFRESH, "refresh", Instant.now());
        Map<TokenType, Token> tokens = Map.of(TokenType.ACCESS, accessToken, TokenType.REFRESH, refreshToken);

        when(tokenManager.generate(userDetails)).thenReturn(tokens);
        when(cookieManager.generate(any(), any(), any())).thenReturn(new Cookie("name", "value"));

        sessionCookieManager.create(userDetails, request, response);

        verify(tokenManager).generate(userDetails);
        verify(cookieManager, times(2)).generate(any(), any(), any());
        verify(response, times(2)).addCookie(any(Cookie.class));
        verify(csrfTokenService).onAuthentication(request, response);
    }

    @Test
    @DisplayName("UT: refresh() when token valid should refresh and add cookie")
    void refresh_whenTokenValid_shouldRefreshAndAddCookie() {
        UUID refreshTokenId = UUID.randomUUID();
        Token accessToken = new Token(TokenType.ACCESS, "new_access", Instant.now());

        when(tokenManager.refresh(refreshTokenId)).thenReturn(accessToken);
        when(cookieManager.generate(any(), any(), any())).thenReturn(new Cookie("name", "value"));

        sessionCookieManager.refresh(refreshTokenId, response);

        verify(tokenManager).refresh(refreshTokenId);
        verify(cookieManager).generate(accessToken.rawToken(), accessToken.expiresAt(), CookieType.ACCESS_TOKEN);
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    @DisplayName("UT: refresh() when token expired should clean cookies and throw exception")
    void refresh_whenTokenExpired_shouldCleanCookiesAndThrow() {
        UUID refreshTokenId = UUID.randomUUID();
        when(tokenManager.refresh(refreshTokenId)).thenThrow(new RefreshTokenExpiredException());

        assertThrows(RefreshTokenExpiredException.class, () -> sessionCookieManager.refresh(refreshTokenId, response));

        verify(cookieManager).cleanAll(response);
    }

    @Test
    @DisplayName("UT: terminate() should deactivate token and clean cookies")
    void terminate_shouldDeactivateAndClean() {
        UUID refreshTokenId = UUID.randomUUID();

        sessionCookieManager.terminate(refreshTokenId, response);

        verify(tokenManager).deactivate(refreshTokenId);
        verify(cookieManager).cleanAll(response);
    }

    @Test
    @DisplayName("UT: terminateAll() should deactivate all tokens and clean cookies")
    void terminateAll_shouldDeactivateAllAndClean() {
        UUID accountId = UUID.randomUUID();

        sessionCookieManager.terminateAll(accountId, response);

        verify(tokenManager).deactivateAll(accountId);
        verify(cookieManager).cleanAll(response);
    }
}
