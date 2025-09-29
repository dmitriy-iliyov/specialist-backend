package com.specialist.profile.models;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.mappers.SpecialistProfileStatusConverter;
import com.specialist.profile.models.enums.SpecialistStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "specialist_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SpecialistProfileEntity extends BaseProfileEntity {

    @Convert(converter = SpecialistProfileStatusConverter.class)
    @Column(nullable = false)
    private SpecialistStatus status;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "has_card", nullable = false)
    private boolean hasCard;

    @Column(name = "card_id")
    private UUID cardId;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = createdAt;
        type = ProfileType.SPECIALIST;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
