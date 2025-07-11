package com.aidcompass.core.security.domain.token.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private UUID id;
    private UUID subjectId;
    private TokenType type;
    private List<String> authorities;
    private Instant issuedAt;
    private Instant expiresAt;
}