package com.aidcompass.auth.domain.refresh_token;

import com.aidcompass.auth.domain.account.models.AccountEntity;
import com.aidcompass.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Data
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "create_at", nullable = false, updatable = false)
    private Instant createdAt;

    public RefreshTokenEntity() {
        this.id = UuidUtils.generateV7();
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }
}
