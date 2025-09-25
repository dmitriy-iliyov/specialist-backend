package com.specialist.contracts.notification;

import java.util.List;

public record InternalAppointmentCancelEvent(
        List<SystemAppointmentResponseDto> appointments
) { }
