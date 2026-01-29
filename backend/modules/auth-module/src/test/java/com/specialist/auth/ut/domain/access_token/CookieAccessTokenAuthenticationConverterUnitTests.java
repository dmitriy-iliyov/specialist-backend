package com.specialist.auth.ut.domain.access_token;

import com.specialist.auth.domain.access_token.AccessTokenDeserializer;
import com.specialist.auth.domain.access_token.CookieAccessTokenAuthenticationConverter;
import com.specialist.auth.domain.access_token.models.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CookieAccessTokenAuthenticationConverterUnitTests {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "__Host-access-token";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "__Host-refresh-token";

    @Mock
    private AccessTokenDeserializer accessTokenDeserializer;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CookieAccessTokenAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        reset(request, accessTokenDeserializer);
    }

    @Test
    @DisplayName("UT: convert() with valid access token cookie should return Authentication")
    void convert_withValidAccessTokenCookie_shouldReturnAuthentication() {
        String rawToken = "valid.raw.token";
        Cookie[] cookies = {new Cookie(ACCESS_TOKEN_COOKIE_NAME, rawToken)};
        AccessToken accessToken = new AccessToken(UUID.randomUUID(), UUID.randomUUID(), List.of("ROLE_USER"), Instant.now(), Instant.now().plusSeconds(300));

        when(request.getCookies()).thenReturn(cookies);
        when(accessTokenDeserializer.deserialize(rawToken)).thenReturn(accessToken);

        Authentication authentication = converter.convert(request);

        assertNotNull(authentication);
        assertTrue(authentication instanceof PreAuthenticatedAuthenticationToken);
        assertEquals(accessToken, authentication.getPrincipal());
        assertEquals(ACCESS_TOKEN_COOKIE_NAME, authentication.getCredentials());

        verify(request, atLeastOnce()).getCookies();
        verify(accessTokenDeserializer).deserialize(rawToken);
        verifyNoMoreInteractions(accessTokenDeserializer);
    }

    @Test
    @DisplayName("UT: convert() for refresh URI should return Authentication with raw refresh token")
    void convert_forRefreshUri_shouldReturnAuthenticationWithRawToken() {
        String rawRefreshToken = "valid.refresh.token";
        Cookie[] cookies = {new Cookie(REFRESH_TOKEN_COOKIE_NAME, rawRefreshToken)};

        when(request.getRequestURI()).thenReturn("/api/auth/refresh");
        when(request.getCookies()).thenReturn(cookies);

        Authentication authentication = converter.convert(request);

        assertNotNull(authentication);
        assertTrue(authentication instanceof PreAuthenticatedAuthenticationToken);
        assertEquals(rawRefreshToken, authentication.getPrincipal());
        assertEquals(REFRESH_TOKEN_COOKIE_NAME, authentication.getCredentials());

        verify(request, atLeastOnce()).getCookies();
        verifyNoInteractions(accessTokenDeserializer);
    }

    @Test
    @DisplayName("UT: convert() for login URI should return null")
    void convert_forLoginUri_shouldReturnNull() {
        Authentication authentication = converter.convert(request);

        assertNull(authentication);
    }

    @Test
    @DisplayName("UT: convert() with no cookies should return null")
    void convert_withNoCookies_shouldReturnNull() {
        when(request.getCookies()).thenReturn(null);

        Authentication authentication = converter.convert(request);

        assertNull(authentication);
        verify(request).getCookies();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("UT: convert() with null or empty access token cookie value should return null")
    void convert_withNullOrEmptyAccessTokenCookieValue_shouldReturnNull(String cookieValue) {
        Cookie[] cookies = {new Cookie(ACCESS_TOKEN_COOKIE_NAME, cookieValue)};
        when(request.getCookies()).thenReturn(cookies);

        Authentication authentication = converter.convert(request);

        assertNull(authentication);
        verify(request, atLeastOnce()).getCookies();
    }
}