package com.aidcompass.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "members")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "creator_rating", nullable = false)
    private double creatorRating;

    @Column(name = "summary_specialist_rating", nullable = false)
    private long summarySpecialistRating;

    @Column(name = "specialist_review_count", nullable = false)
    private long specialistReviewCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    private Long version;


    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
        creatorRating = 0;
        summarySpecialistRating = 0;
        specialistReviewCount = 0;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public String getFullName() {
        if (secondName == null)
            return lastName + " " + firstName;
        return lastName + " " + firstName + " " + secondName;
    }
}