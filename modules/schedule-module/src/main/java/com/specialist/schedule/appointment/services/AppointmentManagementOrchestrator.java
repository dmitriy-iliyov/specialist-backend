package com.specialist.schedule.appointment.services;


import com.specialist.schedule.appointment.models.dto.AppointmentCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;

import java.util.UUID;

public interface AppointmentManagementOrchestrator {
    AppointmentResponseDto save(UUID userId, AppointmentCreateDto dto);

    AppointmentResponseDto update(UUID userId, AppointmentUpdateDto updateDto);

    AppointmentResponseDto complete(Long id, String review);

    AppointmentResponseDto cancel(Long id);
}
