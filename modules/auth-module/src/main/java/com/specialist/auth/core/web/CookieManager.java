package com.specialist.auth.core.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;

public interface CookieManager {
    Cookie generate(String rawToken, Instant expiresAt, CookieType type);

    Cookie clean(CookieType type);

    void cleanAll(HttpServletResponse response);
}
