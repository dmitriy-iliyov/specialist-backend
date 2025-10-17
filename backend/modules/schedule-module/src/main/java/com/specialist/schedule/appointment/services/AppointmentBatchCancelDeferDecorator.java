package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.infrastructure.AppointmentCancelTaskService;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AppointmentBatchCancelDeferDecorator implements AppointmentBatchCancelService {

    private final AppointmentBatchCancelService delegate;
    private final AppointmentCancelTaskService taskService;

    public AppointmentBatchCancelDeferDecorator(@Qualifier("unifiedAppointmentService") AppointmentBatchCancelService delegate,
                                                AppointmentCancelTaskService taskService) {
        this.delegate = delegate;
        this.taskService = taskService;
    }

    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(participantId, date);
        if (batch.hasNext()) {
            Boolean exists = taskService.existsByParticipantIdAndTypeAndDate(
                    participantId, AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA, date
            );
            if (!exists) {
                taskService.save(new AppointmentCancelTaskCreateDto(participantId, AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA, date));
            }
        }
        return batch;
    }

    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(UUID participantId) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantId);
        if (batch.hasNext()) {
            Boolean exists = taskService.existsByParticipantIdAndTypeAndDate(
                    participantId, AppointmentCancelTaskType.CANCEL_BATCH, null
            );
            if (!exists) {
                taskService.save(new AppointmentCancelTaskCreateDto(participantId, AppointmentCancelTaskType.CANCEL_BATCH, null));
            }
        }
        return batch;
    }
}
