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
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
                taskService.save(
                        Set.of(new AppointmentCancelTaskCreateDto(participantId, AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA, date))
                );
            }
        }
        return batch;
    }

    // can be involved only from scheduler after method with single id
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(Set<UUID> ids, LocalDate date) {
        return delegate.cancelBatchByDate(ids, date);
    }

    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(Set<UUID> participantIds) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantIds);
        if (batch.hasNext()) {
            Map<UUID, Boolean> exists = taskService.existsByParticipantIdInAndType(
                    participantIds, AppointmentCancelTaskType.CANCEL_BATCH
            );
            Set<AppointmentCancelTaskCreateDto> dtos = exists.keySet().stream()
                    .filter(participantId -> exists.get(participantId).equals(Boolean.FALSE))
                    .map(participantId ->
                            new AppointmentCancelTaskCreateDto(participantId, AppointmentCancelTaskType.CANCEL_BATCH, null)
                    )
                    .collect(Collectors.toSet());
            if (!dtos.isEmpty()) {
                taskService.save(dtos);
            }
        }
        return batch;
    }
}
