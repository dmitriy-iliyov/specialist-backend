package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AppointmentCancelPutOffDecorator implements AppointmentCancelService {

    private final AppointmentCancelService delegate;
    private final AppointmentCancelTaskService taskService;

    public AppointmentCancelPutOffDecorator(@Qualifier("unifiedAppointmentService") AppointmentCancelService delegate,
                                            AppointmentCancelTaskService taskService) {
        this.delegate = delegate;
        this.taskService = taskService;
    }

    @Override
    public AppointmentResponseDto cancelById(Long id) {
        return delegate.cancelById(id);
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(participantId, date);
        if (batch.hasNext()) {
            taskService.save(new );
        }
        return batch;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(UUID participantId) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantId);
        if (batch.hasNext()) {
            taskService.save();
        }
        return batch;
    }
}
