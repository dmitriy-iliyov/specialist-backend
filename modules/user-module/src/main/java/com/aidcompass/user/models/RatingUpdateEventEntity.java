package com.aidcompass.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "rating_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingUpdateEventEntity {

    @Id
    private UUID id;

    @Column(name = "creatorId", nullable = false)
    private UUID creatorId;

    @Column(name = "review_count", nullable = false)
    private long reviewCount;

    @Column(name = "earned_rating", nullable = false)
    private long earnedRating;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

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
