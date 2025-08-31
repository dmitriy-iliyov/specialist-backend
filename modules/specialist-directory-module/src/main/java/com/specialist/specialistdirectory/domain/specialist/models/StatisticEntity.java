package com.specialist.specialistdirectory.domain.specialist.models;

import com.specialist.specialistdirectory.domain.specialist.mappers.ApproverTypeConverter;
import com.specialist.specialistdirectory.domain.specialist.mappers.CreatorTypeConverter;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.utils.UuidUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "specialists_statistic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticEntity {

    @Id
    private UUID id;

    @Column(name = "creator_type", nullable = false, updatable = false)
    @Convert(converter = CreatorTypeConverter.class)
    private CreatorType creatorType;

    @Column(name = "approver_id")
    private UUID approverId;

    @Column(name = "approver_type")
    @Convert(converter = ApproverTypeConverter.class)
    private ApproverType approverType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToOne(mappedBy = "statistic", optional = false)
    private SpecialistEntity specialist;

    public StatisticEntity(CreatorType creatorType) {
        this.creatorType = creatorType;
    }

    @PrePersist
    public void prePersist() {
        id = UuidUtils.generateV7();
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
