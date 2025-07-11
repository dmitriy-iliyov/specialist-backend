package com.aidcompass.core.security.domain.service;

import com.aidcompass.core.security.domain.authority.models.AuthorityEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceEntity {

    @Id
    private UUID id;

    @Column(name = "service_name", nullable = false, unique = true, updatable = false)
    private String serviceName;

    @Column(nullable = false, updatable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", nullable = false, updatable = false)
    private AuthorityEntity authorityEntity;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
