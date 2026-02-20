package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface AppointmentBatchCancelService {
    BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date);
    BatchResponse<AppointmentResponseDto> cancelBatchByDate(Set<UUID> ids, LocalDate date);
    BatchResponse<AppointmentResponseDto> cancelBatch(Set<UUID> participantIds);
}
