package com.aidcompass.auth.domain.access_token.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AccessToken(
        UUID id,
        UUID subjectId,
        List<String> authorities,
        Instant createdAt,
        Instant expiresAt
) { }
