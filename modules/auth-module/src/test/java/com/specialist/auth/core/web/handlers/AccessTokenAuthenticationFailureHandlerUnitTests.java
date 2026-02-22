package com.specialist.auth.core.web.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.web.CookieManager;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenAuthenticationFailureHandlerUnitTests {

    @Mock
    private CookieManager cookieManager;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AccessTokenAuthenticationFailureHandler failureHandler;

    @Test
    @DisplayName("UT: onAuthenticationFailure() with RefreshTokenExpiredException should clean all cookies")
    void onAuthenticationFailure_refreshTokenExpired_shouldCleanAllCookies() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        AuthenticationException exception = new RefreshTokenExpiredException();

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(cookieManager).cleanAll(response);
    }

    @Test
    @DisplayName("UT: onAuthenticationFailure() with AccessTokenExpiredException should clean access token cookie")
    void onAuthenticationFailure_accessTokenExpired_shouldCleanAccessTokenCookie() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        AuthenticationException exception = new AccessTokenExpiredException();

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(cookieManager).clean(TokenType.ACCESS.getCookieType());
    }

    @Test
    @DisplayName("UT: onAuthenticationFailure() with InvalidJwtSignatureException should set bad request status")
    void onAuthenticationFailure_invalidJwtSignature_shouldSetBadRequestStatus() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        AuthenticationException exception = new InvalidJwtSignatureException(new Exception());

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("UT: onAuthenticationFailure() with JwtParseException should set internal server error status")
    void onAuthenticationFailure_jwtParseException_shouldSetInternalServerErrorStatus() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        AuthenticationException exception = new JwtParseException(new Exception());

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("UT: onAuthenticationFailure() with other exception should set unauthorized status")
    void onAuthenticationFailure_otherException_shouldSetUnauthorizedStatus() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        AuthenticationException exception = new AuthenticationException("other") {};

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
