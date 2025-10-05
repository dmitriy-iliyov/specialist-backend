package com.specialist.auth.domain.account.models;

import com.specialist.auth.domain.account.mappers.AccountDeleteTaskStatusConverter;
import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "account_delete_tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeleteTaskEntity {

    @Id
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "role", nullable = false)
    private String stringRole;

    @Convert(converter = AccountDeleteTaskStatusConverter.class)
    @Column(nullable = false)
    private AccountDeleteTaskStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public AccountDeleteTaskEntity(UUID accountId, String stringRole) {
        this.accountId = accountId;
        this.stringRole = stringRole;
    }

    @PrePersist
    public void prePersist() {
        id = UuidUtils.generateV7();
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
