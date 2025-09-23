package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;

import java.util.List;
import java.util.UUID;

public interface SystemAppointmentService {
    void deleteAll(UUID participantId);

    List<Long> markBatchAsSkipped(int batchSize);

    BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize, int page);
}
