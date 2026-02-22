package com.specialist.auth.core.web;

import com.specialist.auth.core.TokenType;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CookieGeneratorUnitTests {

    @InjectMocks
    CookieManagerImpl cookieManager;

    @Test
    @DisplayName("UT: generate() with REFRESH should create correct cookie")
    public void generate_refreshToken_shouldCreateCorrectCookie() {
        Instant expiresAt = Instant.now().plusSeconds(3600);

        Cookie cookie = cookieManager.generate("refreshTokenValue", expiresAt, TokenType.REFRESH.getCookieType());

        assertEquals("__Host-refresh-token", cookie.getName());
        assertEquals("refreshTokenValue", cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertEquals(null, cookie.getDomain());
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertEquals(3600, cookie.getMaxAge(), 1);
    }

    @Test
    @DisplayName("UT: generate() with ACCESS should create correct cookie")
    public void generate_accessToken_shouldCreateCorrectCookie() {
        Instant expiresAt = Instant.now().plusSeconds(1800);

        Cookie cookie = cookieManager.generate("accessTokenValue", expiresAt, TokenType.ACCESS.getCookieType());

        assertEquals("__Host-access-token", cookie.getName());
        assertEquals("accessTokenValue", cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertEquals(null, cookie.getDomain());
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertEquals(1800, cookie.getMaxAge(), 1);
    }

    @Test
    @DisplayName("UT: generateEmpty() with REFRESH should create empty cookie with maxAge=0")
    public void generateEmpty_refreshToken_shouldCreateEmptyCookie() {
        Cookie cookie = cookieManager.clean(TokenType.REFRESH.getCookieType());

        assertEquals("__Host-refresh-token", cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertEquals(null, cookie.getDomain());
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertEquals(0, cookie.getMaxAge());
    }

    @Test
    @DisplayName("UT: generateEmpty() with ACCESS should create empty cookie with maxAge=0")
    public void generateEmpty_accessToken_shouldCreateEmptyCookie() {
        Cookie cookie = cookieManager.clean(TokenType.ACCESS.getCookieType());

        assertEquals("__Host-access-token", cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertEquals(null, cookie.getDomain());
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertEquals(0, cookie.getMaxAge());
    }
}