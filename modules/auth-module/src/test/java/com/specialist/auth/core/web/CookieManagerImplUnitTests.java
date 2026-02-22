package com.specialist.auth.core.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CookieManagerImplUnitTests {

    @InjectMocks
    private CookieManagerImpl cookieManager;

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("UT: generate() should return configured cookie")
    void generate_shouldReturnConfiguredCookie() {
        String rawToken = "token";
        Instant expiresAt = Instant.now().plusSeconds(3600);
        CookieType type = CookieType.ACCESS_TOKEN;

        Cookie cookie = cookieManager.generate(rawToken, expiresAt, type);

        assertNotNull(cookie);
        assertEquals("__Host-access-token", cookie.getName());
        assertEquals(rawToken, cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertTrue(cookie.getMaxAge() > 0);
    }

    @Test
    @DisplayName("UT: clean() should return expired cookie")
    void clean_shouldReturnExpiredCookie() {
        CookieType type = CookieType.REFRESH_TOKEN;

        Cookie cookie = cookieManager.clean(type);

        assertNotNull(cookie);
        assertEquals("__Host-refresh-token", cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals(0, cookie.getMaxAge());
    }

    @Test
    @DisplayName("UT: cleanAll() should add expired cookies to response")
    void cleanAll_shouldAddExpiredCookies() {
        cookieManager.cleanAll(response);
        verify(response, times(3)).addCookie(any(Cookie.class));
    }
}
