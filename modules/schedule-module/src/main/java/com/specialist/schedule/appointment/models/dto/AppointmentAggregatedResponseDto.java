package com.specialist.schedule.appointment.models.dto;

import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;

public record AppointmentAggregatedResponseDto(
        AppointmentResponseDto appointment,
        UnifiedProfileResponseDto participant
) { }