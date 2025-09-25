package com.specialist.contracts.notification;

public record ExternalAppointmentCancelEvent(
        SystemAppointmentResponseDto appointment,
        SystemShortProfileResponseDto user,
        SystemShortProfileResponseDto specialist
) { }
