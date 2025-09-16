package com.specialist.user.mappers;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.contracts.user.ProfileType;
import com.specialist.user.models.dtos.PublicSpecialistResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PublicSpecialistAggregatedResponseDto extends PublicSpecialistResponseDto {

    private final ManagedSpecialistResponseDto card;

    public PublicSpecialistAggregatedResponseDto(UUID id, ProfileType type, String fullName, String aboutMe, boolean hasManagedEntity,
                                                 ManagedSpecialistResponseDto card) {
        super(id, type, fullName, aboutMe, hasManagedEntity);
        this.card = card;
    }
}
