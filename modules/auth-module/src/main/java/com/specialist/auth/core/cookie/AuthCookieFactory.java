package com.specialist.auth.core.cookie;

import jakarta.servlet.http.Cookie;

import java.time.Duration;
import java.time.Instant;

public final class AuthCookieFactory {

    public static Cookie generate(String rawToken, Instant expiresAt, TokenType type) {
        if (type.equals(TokenType.REFRESH)) {
            Cookie cookie = new Cookie("__Host-refresh-token", rawToken);
            cookie.setPath("/");
            cookie.setDomain(null);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) Duration.between(Instant.now(), expiresAt).getSeconds());
            return cookie;
        } else {
            Cookie cookie = new Cookie("__Host-access-token", rawToken);
            cookie.setPath("/");
            cookie.setDomain(null);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) Duration.between(Instant.now(), expiresAt).getSeconds());
            return cookie;
        }
    }

    public static Cookie generateEmpty(TokenType type) {
        Cookie cookie;
        if (type.equals(TokenType.REFRESH)) {
            cookie = new Cookie("__Host-refresh-token", "");
        } else {
            cookie = new Cookie("__Host-access-token", "");
        }
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }
}
