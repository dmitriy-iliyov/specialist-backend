package com.specialist.contracts.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.user.UserType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PublicUserResponseDto extends BaseResponseDto {

    @JsonProperty("creator_rating")
    private final Double creatorRating;

    public PublicUserResponseDto(UUID id, UserType type,  String fullName, double creatorRating) {
        super(id, type, fullName);
        this.creatorRating = creatorRating;
    }
}
