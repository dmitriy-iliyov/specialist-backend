package com.aidcompass.user.models.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record UserUpdateDto(
        @JsonUnwrapped
        UserDto dto,

        String email
) { }
