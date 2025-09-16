package com.specialist.profile.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.profile.ProfileType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PrivateUserResponseDto extends BasePrivateResponseDto {

    @JsonProperty("creator_rating")
    private final double creatorRating;

    public PrivateUserResponseDto(UUID id, ProfileType type, String fullName, String email, LocalDateTime createdAt, LocalDateTime updatedAt, double creatorRating) {
        super(id, type, fullName, email, createdAt, updatedAt);
        this.creatorRating = creatorRating;
    }
}
