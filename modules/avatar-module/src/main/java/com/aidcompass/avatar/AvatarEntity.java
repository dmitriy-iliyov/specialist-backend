package com.aidcompass.avatar;

import com.aidcompass.core.general.utils.uuid.UuidFactory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "avatars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", unique = true, nullable = false)
    private UUID userId;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public AvatarEntity(UUID userId, String avatarUrl) {
        this.id = UuidFactory.generate();
        this.userId = userId;
        this.avatarUrl = avatarUrl;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
