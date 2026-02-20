package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppointmentCancelTaskOrchestratorImpl implements AppointmentCancelTaskOrchestrator {

    private final AppointmentCancelTaskService taskService;
    private final Map<AppointmentCancelTaskType, Function<List<AppointmentCancelTaskResponseDto>, BatchResponse<AppointmentResponseDto>>> operations;

    public AppointmentCancelTaskOrchestratorImpl(@Qualifier("appointmentBatchCancelNotifyDecorator") AppointmentBatchCancelService appointmentService,
                                                 AppointmentCancelTaskService taskService) {
        this.taskService = taskService;
        this.operations = Map.of(
                AppointmentCancelTaskType.CANCEL_BATCH, (dtos) -> {
                    Set<UUID> ids = dtos.stream()
                            .map(AppointmentCancelTaskResponseDto::participantId)
                            .collect(Collectors.toSet());
                    return appointmentService.cancelBatch(ids);
                },
                AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA, (dtos) -> {
                    Set<UUID> ids = dtos.stream()
                            .map(AppointmentCancelTaskResponseDto::participantId)
                            .collect(Collectors.toSet());
                    return appointmentService.cancelBatchByDate(ids, dtos.get(0).date());
                }
        );
    }

    @Transactional
    @Override
    public void cancelBatch(AppointmentCancelTaskType taskType, int batchSize) {
        List<AppointmentCancelTaskResponseDto> dto = taskService.findBatchByType(taskType, batchSize);
        if (!dto.isEmpty()) {
            var operation = operations.get(taskType);
            if (operation == null) {
                throw new IllegalStateException("Unexpected taskType={" + taskType + "}");
            }
            BatchResponse<AppointmentResponseDto> batch = operation.apply(dto);
            if (!batch.hasNext()) {
                taskService.deleteBatch(dto.stream().map(AppointmentCancelTaskResponseDto::id).collect(Collectors.toSet()));
            }
        }
    }
}
