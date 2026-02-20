package com.specialist.auth.core;

import java.time.Instant;

public record Token(
        TokenType type,
        String rawToken,
        Instant expiresAt
) { }
