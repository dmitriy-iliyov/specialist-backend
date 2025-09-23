package com.specialist.schedule.appointment.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.models.dto.*;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface AppointmentFacade {
    AppointmentResponseDto save(UUID userId, AppointmentCreateDto dto);

    AppointmentResponseDto update(UUID userId, AppointmentUpdateDto updateDto);

    AppointmentResponseDto complete(UUID participantId, Long id, String review);

    AppointmentResponseDto cancel(UUID participantId, Long id);

    AppointmentResponseDto findById(UUID participantId, Long id);

    PageResponse<AppointmentAggregatedResponseDto> findAllByFilter(UUID participantId, ProfileType profileType,
                                                                   AppointmentFilter filter);
}
