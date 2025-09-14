package com.specialist.contracts.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.user.ProfileType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UnifiedProfileResponseDto extends BaseResponseDto {

    @JsonProperty("creator_rating")
    private final Double creatorRating;

    public UnifiedProfileResponseDto(UUID id, ProfileType type, String fullName, double creatorRating) {
        super(id, type, fullName);
        this.creatorRating = creatorRating;
    }
}
