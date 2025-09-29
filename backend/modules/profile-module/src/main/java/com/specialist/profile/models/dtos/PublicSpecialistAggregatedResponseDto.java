package com.specialist.profile.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
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
