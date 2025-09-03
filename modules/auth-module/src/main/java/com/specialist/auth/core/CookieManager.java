package com.specialist.auth.core;

import com.specialist.auth.core.models.TokenType;
import jakarta.servlet.http.Cookie;

import java.time.Instant;

public interface CookieManager {
    Cookie generate(String rawToken, Instant expiresAt, TokenType type);

    Cookie clean(TokenType type);

    Cookie cleanCsrf();
}
