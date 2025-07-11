package com.aidcompass.specialistdirectory.domain.specialist_type.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "specialist_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id", nullable = false, updatable = false)
    private UUID creatorId;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public TypeEntity(String title, boolean isApproved) {
        this.title = title;
        this.isApproved = isApproved;
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
