package com.specialist.contracts.user.dto;

import java.util.UUID;

public record ShortProfileCreateDto(
        UUID id,
        String email
) { }
