package com.aidcompass.specialistdirectory.domain.review.models;

import com.aidcompass.specialistdirectory.domain.review.mappers.DeliveryStateConverter;
import com.aidcompass.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.aidcompass.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "review_buffers")
@Data
@AllArgsConstructor
public class ReviewBufferEntity {

    @Id
    private UUID id;

    @Column(name = "creator_id", nullable = false, updatable = false)
    private UUID creatorId;

    @Column(name = "summary_rating", nullable = false)
    private long summaryRating;

    @Column(name = "review_count", nullable = false, updatable = false)
    private long reviewCount;

    @Convert(converter = DeliveryStateConverter.class)
    @Column(name = "delivery_state", nullable = false)
    private DeliveryState deliveryState;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public ReviewBufferEntity() {
        this.id = UuidUtils.generateV7();
    }

    public ReviewBufferEntity(UUID creatorId, long summaryRating) {
        this.id = UuidUtils.generateV7();
        this.creatorId = creatorId;
        this.summaryRating = summaryRating;
        this.reviewCount = 1;
        this.deliveryState = DeliveryState.PREPARE;
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
