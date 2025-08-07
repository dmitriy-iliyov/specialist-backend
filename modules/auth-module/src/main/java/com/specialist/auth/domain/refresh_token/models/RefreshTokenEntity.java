package com.specialist.auth.domain.refresh_token.models;

import com.specialist.auth.domain.refresh_token.RefreshTokenStatusConverter;
import com.specialist.utils.UuidUtils;
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

    @Column(name = "subject_id", nullable = false)
    private UUID subjectId;

    @Column(nullable = false)
    private String authorities;

    @Convert(converter = RefreshTokenStatusConverter.class)
    @Column(nullable = false)
    private RefreshTokenStatus status;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "create_at", nullable = false, updatable = false)
    private Instant createdAt;

    public RefreshTokenEntity() {
        this.id = UuidUtils.generateV7();
    }
}
