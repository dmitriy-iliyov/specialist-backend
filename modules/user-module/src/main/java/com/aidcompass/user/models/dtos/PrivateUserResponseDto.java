package com.aidcompass.user.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class PrivateUserResponseDto {
    private final UUID id;

    @JsonProperty("full_name")
    private final String fullName;

    private final String email;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("creator_rating")
    private final double creatorRating;

    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private final LocalDateTime updatedAt;
}
