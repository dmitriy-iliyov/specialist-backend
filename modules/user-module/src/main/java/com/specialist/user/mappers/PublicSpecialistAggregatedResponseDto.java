package com.specialist.user.mappers;

import com.specialist.contracts.specialistdirectory.ManagedSpecialistResponseDto;
import com.specialist.contracts.user.UserType;
import com.specialist.user.models.dtos.PublicSpecialistResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PublicSpecialistAggregatedResponseDto extends PublicSpecialistResponseDto {

    private final ManagedSpecialistResponseDto card;

    public PublicSpecialistAggregatedResponseDto(UUID id, UserType type, String fullName, String aboutMe, boolean hasManagedEntity,
                                                 ManagedSpecialistResponseDto card) {
        super(id, type, fullName, aboutMe, hasManagedEntity);
        this.card = card;
    }
}
