package com.specialist.contracts.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.profile.ProfileType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public abstract class BaseResponseDto {

    private final UUID id;

    private final ProfileType type;

    @JsonProperty("full_name")
    private final String fullName;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
