package com.specialist.auth.core;

import com.specialist.auth.core.models.TokenType;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public final class CookieManagerImpl implements CookieManager {

    @Override
    public Cookie generate(String rawToken, Instant expiresAt, TokenType type) {
        Cookie cookie;
        if (type.equals(TokenType.REFRESH)) {
            cookie = new Cookie("__Host-refresh-token", rawToken);
        } else {
            cookie = new Cookie("__Host-access-token", rawToken);
        }
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) Duration.between(Instant.now(), expiresAt).getSeconds());
        return cookie;
    }

    @Override
    public Cookie clean(TokenType type) {
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

    @Override
    public Cookie cleanCsrf() {
        Cookie cookie = new Cookie("XSRF-TOKEN", "");
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }
}
