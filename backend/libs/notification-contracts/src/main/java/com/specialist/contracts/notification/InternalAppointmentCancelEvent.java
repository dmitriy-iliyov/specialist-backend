package com.specialist.contracts.notification;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record InternalAppointmentCancelEvent(
        Set<UUID> initiatorIds,
        List<SystemAppointmentResponseDto> appointments
) { }
