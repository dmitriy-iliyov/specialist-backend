package com.specialist.specialistdirectory.domain.type.models;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "specialist_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"specialists", "translates"})
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type", cascade = CascadeType.REMOVE)
    private List<SpecialistEntity> specialists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type", cascade = CascadeType.REMOVE)
    private List<TranslateEntity> translates = new ArrayList<>();

    // for db initializing
    public TypeEntity(String title, boolean isApproved) {
        this.creatorId = UUID.fromString("aa772379-dbeb-45ce-ab67-b34a249e09c8");
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
