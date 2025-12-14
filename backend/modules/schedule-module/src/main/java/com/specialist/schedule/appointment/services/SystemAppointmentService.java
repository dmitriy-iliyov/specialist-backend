package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;

import java.util.List;

public interface SystemAppointmentService {
    List<Long> skipBatch(int batchSize);
    BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize);
}
