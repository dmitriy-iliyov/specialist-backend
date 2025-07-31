package com.aidcompass.auth.domain.access_token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "deactivated_access_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeactivatedAccessTokenEntity {

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
