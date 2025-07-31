package com.aidcompass.auth.domain.refresh_token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "deactivated_refresh_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeactivatedRefreshTokenEntity {

    @Id
    private UUID id;

    @Column(name = "token_id", nullable = false)
    private UUID tokenId;

    @Column(name = "deactivated_at", nullable = false, updatable = false)
    private Instant deactivatedAt;

    @PrePersist
    public void prePersist() {
        deactivatedAt = Instant.now();
    }
}
