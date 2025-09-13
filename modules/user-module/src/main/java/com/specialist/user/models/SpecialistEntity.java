package com.specialist.user.models;

import com.specialist.contracts.user.UserType;
import com.specialist.user.mappers.SpecialistStatusConverter;
import com.specialist.user.models.enums.SpecialistStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "specialist_pforiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SpecialistEntity extends BaseEntity {

    @Convert(converter = SpecialistStatusConverter.class)
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
        type = UserType.SPECIALIST;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
