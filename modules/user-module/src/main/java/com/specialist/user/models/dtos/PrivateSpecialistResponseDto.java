package com.specialist.user.models.dtos;

import com.specialist.contracts.user.ProfileType;
import com.specialist.user.models.enums.SpecialistStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PrivateSpecialistResponseDto extends BasePrivateResponseDto {

    private final SpecialistStatus status;

    private final String aboutMe;

    private final boolean hasCard;

    public PrivateSpecialistResponseDto(UUID id, ProfileType type, String fullName, String email,
                                        SpecialistStatus status, String aboutMe, boolean hasCard,
                                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, fullName, email, createdAt, updatedAt);
        this.status = status;
        this.aboutMe = aboutMe;
        this.hasCard = hasCard;
    }

    public boolean hasCard() {
        return hasCard;
    }
}

