package com.aidcompass.auth.domain.service_account.models;

import com.aidcompass.auth.domain.authority.AuthorityEntity;
import com.aidcompass.auth.domain.role.RoleEntity;
import com.aidcompass.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "service_accounts")
@AllArgsConstructor
@Data
@ToString(exclude = {"role", "authorities"})
public class ServiceAccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String secret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "authority_service_account_relation",
            joinColumns = @JoinColumn(name = "service_account_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "authority_id", nullable = false)
    )
    private List<AuthorityEntity> authorities;

    @Column(name = "creator_id", nullable = false, updatable = false)
    private UUID creatorId;

    @Column(name = "updater_id", nullable = false)
    private UUID updaterId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public ServiceAccountEntity() {
        this.id = UuidUtils.generateV7();
    }

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
