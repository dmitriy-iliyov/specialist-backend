package com.specialist.profile.models;

import com.specialist.profile.mappers.ProcessingStatusConverter;
import com.specialist.profile.models.enums.ProcessingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "rating_update_events")
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

    @Convert(converter = ProcessingStatusConverter.class)
    @Column(nullable = false)
    private ProcessingStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }
}
