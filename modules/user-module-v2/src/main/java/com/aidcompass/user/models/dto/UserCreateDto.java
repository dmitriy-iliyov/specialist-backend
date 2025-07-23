package com.aidcompass.user.models.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record UserCreateDto(
        @JsonUnwrapped
        UserDto dto
) { }
