package com.specialist.schedule.appointment.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.models.dto.AppointmentAggregatedResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentFilter;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface AppointmentAggregateStrategy {
    ProfileType getType();
    PageResponse<AppointmentAggregatedResponseDto> aggregate(UUID participantId, AppointmentFilter filter);
}
