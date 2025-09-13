package com.specialist.user.models.dtos;

import com.specialist.contracts.specialistdirectory.ManagedSpecialistResponseDto;
import com.specialist.contracts.user.UserType;
import com.specialist.user.models.enums.SpecialistStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class PrivateSpecialistAggregatedResponseDto extends PrivateSpecialistResponseDto {

    private final ManagedSpecialistResponseDto card;

    public PrivateSpecialistAggregatedResponseDto(UUID id, UserType type, String fullName, String email,
                                                  SpecialistStatus status, String aboutMe, boolean hasCard,
                                                  ManagedSpecialistResponseDto card,
                                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, fullName, email, status, aboutMe, hasCard, createdAt, updatedAt);
        this.card = card;
    }
}
