package com.specialist.contracts.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.user.UserType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public abstract class BaseResponseDto {

    private final UUID id;

    private final UserType type;

    @JsonProperty("full_name")
    private final String fullName;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
