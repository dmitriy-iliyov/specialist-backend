package com.specialist.user.models.dtos;

import com.specialist.contracts.user.ProfileType;
import com.specialist.contracts.user.dto.BaseResponseDto;
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
