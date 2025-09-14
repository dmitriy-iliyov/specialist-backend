package com.specialist.user.models;

import com.specialist.contracts.user.ProfileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "user_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Column(name = "creator_rating", nullable = false)
    private double creatorRating;

    @Column(name = "summary_specialist_rating", nullable = false)
    private long summarySpecialistRating;

    @Column(name = "specialist_review_count", nullable = false)
    private long specialistReviewCount;

    @Version
    private Long version;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
        type = ProfileType.USER;
        creatorRating = 0;
        summarySpecialistRating = 0;
        specialistReviewCount = 0;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}