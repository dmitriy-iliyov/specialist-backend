package com.specialist.contracts.user.dto;

import java.util.UUID;

public record ShortUserCreateDto(
        UUID id,
        String email
) { }
