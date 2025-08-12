package com.specialist.auth.domain.account.models;

import com.specialist.auth.domain.account.mappers.LockReasonTypeConverter;
import com.specialist.auth.domain.account.mappers.UnableReasonTypeConverter;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.models.enums.UnableReason;
import com.specialist.auth.domain.auth_provider.Provider;
import com.specialist.auth.domain.auth_provider.ProviderConverter;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@ToString(exclude = {"role", "authorities"})
public class AccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    @Convert(converter = ProviderConverter.class)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "authority_account_relation",
            joinColumns = @JoinColumn(name = "account_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "authority_id", nullable = false)
    )
    private List<AuthorityEntity> authorities;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @Column(name = "lock_reason")
    @Convert(converter = LockReasonTypeConverter.class)
    private LockReason lockReason;

    @Column(name = "lock_term")
    private Instant lockTerm;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Convert(converter = UnableReasonTypeConverter.class)
    @Column(name = "unable_reason")
    private UnableReason unableReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public AccountEntity() {
        this.id = UuidUtils.generateV7();
        this.isEnabled = false;
        this.isLocked = false;
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
