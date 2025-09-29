package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentFilter;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface AppointmentQueryService {
    PageResponse<AppointmentResponseDto> findAllByFilter(UUID participantId, AppointmentFilter filter);
}
