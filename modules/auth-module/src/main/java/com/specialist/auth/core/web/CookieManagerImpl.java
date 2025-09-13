package com.specialist.auth.core;

import com.specialist.auth.core.models.CookieType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Service
@Slf4j
public final class CookieManagerImpl implements CookieManager {

    private final Map<CookieType, String> cookieNames = Map.of(
            CookieType.REFRESH_TOKEN, "__Host-refresh-token",
            CookieType.ACCESS_TOKEN, "__Host-access-token",
            CookieType.CSRF_TOKEN, "XSRF-TOKEN"
    );

    @Override
    public Cookie generate(String rawToken, Instant expiresAt, CookieType type) {
        Cookie cookie = new Cookie(getCookieName(type), rawToken);
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) Duration.between(Instant.now(), expiresAt).getSeconds());
        return cookie;
    }

    @Override
    public Cookie clean(CookieType type) {
        Cookie cookie = new Cookie(getCookieName(type), "");
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }

    private String getCookieName(CookieType type) {
        String cookieName = cookieNames.get(type);
        if (cookieName == null) {
            log.error("Cookie name is null, tokenType:{}, time:{}", type, Instant.now());
        }
        return cookieName;
    }

    @Override
    public void cleanAll(HttpServletResponse response) {
        for (CookieType type: cookieNames.keySet()) {
            response.addCookie(clean(type));
        }
    }
}
