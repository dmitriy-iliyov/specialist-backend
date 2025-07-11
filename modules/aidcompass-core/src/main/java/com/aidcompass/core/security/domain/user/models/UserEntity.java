package com.aidcompass.core.security.domain.user.models;

import com.aidcompass.core.security.domain.authority.models.AuthorityEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity{

    @Id
    private UUID id;

    @Column(name = "email_id", unique = true)
    private Long emailId;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authrity_id")
    )
    private List<AuthorityEntity> authorities = new ArrayList<>();


    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
        isExpired = false;
        isLocked = false;
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}
