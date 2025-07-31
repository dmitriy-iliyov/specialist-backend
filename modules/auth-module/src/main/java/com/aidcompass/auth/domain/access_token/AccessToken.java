package com.aidcompass.auth.domain.access_token;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class AccessToken {
    private UUID id;
    private UUID subjectId;
    private List<String> authorities;
    private Instant createdAt;
    private Instant expiresAt;
}
