package com.specialist.contracts.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PublicUserResponseDto extends BaseResponseDto {

    @JsonProperty("creator_rating")
    private final double creatorRating;

    public PublicUserResponseDto(UUID id, String fullName, double creatorRating) {
        super(id, fullName);
        this.creatorRating = creatorRating;
    }
}
