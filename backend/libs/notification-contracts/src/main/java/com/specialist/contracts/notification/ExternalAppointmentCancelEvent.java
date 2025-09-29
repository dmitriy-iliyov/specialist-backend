package com.specialist.contracts.notification;

public record ExternalAppointmentCancelEvent(
        InitiatorType type,
        SystemAppointmentResponseDto appointment,
        SystemShortProfileResponseDto recipient
) { }
