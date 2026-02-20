package com.specialist.profile.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;

import java.util.UUID;

public class PublicUserResponseDto extends BaseResponseDto {

    @JsonProperty("creator_rating")
    private final Double creatorRating;

    public PublicUserResponseDto(UUID id, ProfileType type, String fullName, double creatorRating) {
        super(id, type, fullName);
        this.creatorRating = creatorRating;
    }
}
