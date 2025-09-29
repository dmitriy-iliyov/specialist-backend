package com.specialist.profile.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PublicSpecialistResponseDto extends BaseResponseDto {

    private final String aboutMe;

    private final boolean hasCard;

    public PublicSpecialistResponseDto(UUID id, ProfileType type, String fullName, String aboutMe, boolean hasCard) {
        super(id, type, fullName);
        this.aboutMe = aboutMe;
        this.hasCard = hasCard;
    }

    public boolean hasCard() {
        return hasCard;
    }
}
