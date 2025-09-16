package com.specialist.contracts.profile.dto;

import java.util.UUID;

public record ShortProfileCreateDto(
        UUID id,
        String email
) { }
