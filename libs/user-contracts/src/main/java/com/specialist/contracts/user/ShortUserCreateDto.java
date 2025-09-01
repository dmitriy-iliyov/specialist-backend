package com.specialist.contracts.user;

import java.util.UUID;

public record ShortUserCreateDto(
        UUID id,
        String email
) { }
