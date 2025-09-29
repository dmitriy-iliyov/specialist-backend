package com.specialist.contracts.notification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record SystemAppointmentResponseDto(
        Long id,
        UUID userId,
        UUID specialistId,
        LocalDate date,
        LocalTime start,
        String type
) { }
