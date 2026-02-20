package com.specialist.specialistdirectory.domain.review.models;

import com.specialist.specialistdirectory.domain.review.mappers.DeliveryStateConverter;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.utils.uuid.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "creator_rating_buffers")
@Data
@AllArgsConstructor
public class CreatorRatingBufferEntity {

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

    @Version
    private Long version;


    public CreatorRatingBufferEntity() {
        this.id = UuidUtils.generateV7();
    }

    public CreatorRatingBufferEntity(UUID creatorId, long summaryRating) {
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
