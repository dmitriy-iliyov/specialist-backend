package com.specialist.auth.core.models;

import java.time.Instant;

public record Token(
        TokenType type,
        String rawToken,
        Instant expiresAt
) { }
