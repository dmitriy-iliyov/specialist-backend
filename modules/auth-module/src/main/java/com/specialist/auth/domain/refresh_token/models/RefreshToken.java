package com.specialist.auth.domain.refresh_token.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RefreshToken(
        UUID id,
        UUID accountId,
        List<String> authorities,
        Instant expiresAt
) { }
