package com.specialist.contracts.notification;

import java.util.List;
import java.util.UUID;

public record InternalAppointmentCancelEvent(
        UUID initiatorId,
        List<SystemAppointmentResponseDto> appointments
) { }
