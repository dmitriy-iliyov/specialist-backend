package com.specialist.contracts.notification;

import java.util.UUID;

public record SystemProfileResponseDto(
        UUID id,
        String type,
        String fullName,
        String email
) { }
