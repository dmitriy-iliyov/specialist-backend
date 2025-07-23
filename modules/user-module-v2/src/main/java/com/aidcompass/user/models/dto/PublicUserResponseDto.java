package com.aidcompass.user.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class PublicUserResponseDto {
    private final UUID id;

    @JsonProperty("full_name")
    private final String fullName;

    private final String email;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("creator_rating")
    private final double creatorRating;
}
