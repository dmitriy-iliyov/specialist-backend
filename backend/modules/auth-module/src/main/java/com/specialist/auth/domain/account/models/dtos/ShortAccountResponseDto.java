package com.specialist.auth.domain.account.models.dtos;

import java.util.UUID;

public record ShortAccountResponseDto(
        UUID id,
        String email
) { }