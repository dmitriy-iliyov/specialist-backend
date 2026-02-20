package com.specialist.profile.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PublicSpecialistAggregatedResponseDto extends PublicSpecialistResponseDto {

    private final ExternalManagedSpecialistResponseDto card;

    public PublicSpecialistAggregatedResponseDto(UUID id, ProfileType type, String fullName, String aboutMe, boolean hasManagedEntity,
                                                 ExternalManagedSpecialistResponseDto card) {
        super(id, type, fullName, aboutMe, hasManagedEntity);
        this.card = card;
    }
}
