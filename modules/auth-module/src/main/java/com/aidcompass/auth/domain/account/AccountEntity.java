package com.aidcompass.auth.domain.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;
import com.aidcompass.

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
public class AccountEntity {

    @Id
    private UUID id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public AccountEntity() {
        //this.id =
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
