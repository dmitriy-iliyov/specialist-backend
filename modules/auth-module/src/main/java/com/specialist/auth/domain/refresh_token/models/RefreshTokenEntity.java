package com.specialist.auth.domain.refresh_token.models;

import com.specialist.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private String authorities;

    @Column(name = "create_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    public RefreshTokenEntity(UUID accountId, String authorities, Instant createdAt, Instant expiresAt) {
        this.accountId = accountId;
        this.authorities = authorities;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    public void prePersist() {
        this.id = UuidUtils.generateV7();
    }
}
