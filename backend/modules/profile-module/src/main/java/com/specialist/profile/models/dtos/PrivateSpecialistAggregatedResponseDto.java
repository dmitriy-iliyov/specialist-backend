package com.specialist.profile.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.profile.models.enums.SpecialistStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class PrivateSpecialistAggregatedResponseDto extends PrivateSpecialistResponseDto {

    private final ManagedSpecialistResponseDto card;

    public PrivateSpecialistAggregatedResponseDto(UUID id, ProfileType type, String fullName, String email,
                                                  SpecialistStatus status, String aboutMe, boolean hasCard,
                                                  ManagedSpecialistResponseDto card,
                                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, fullName, email, status, aboutMe, hasCard, createdAt, updatedAt);
        this.card = card;
    }
}
