package com.specialist.profile.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class BasePrivateResponseDto extends BaseResponseDto {

    private final String email;

    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private final LocalDateTime updatedAt;

    public BasePrivateResponseDto(UUID id, ProfileType type, String fullName, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, fullName);
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
