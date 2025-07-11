package com.aidcompass.core.security.domain.user.models.dto;


public record UserUpdateDto(
        String email,
        String password
) { }
