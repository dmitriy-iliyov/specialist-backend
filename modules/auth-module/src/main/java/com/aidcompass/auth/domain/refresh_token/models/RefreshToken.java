package com.aidcompass.auth.domain.refresh_token.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RefreshToken(
        UUID id,
        UUID subjectId,
        List<String> authorities,
        RefreshTokenStatus status,
        Instant expiresAt
) { }
