package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Function;

@Service
public class AppointmentCancelTaskOrchestratorImpl implements AppointmentCancelTaskOrchestrator {

    private final AppointmentCancelTaskService taskService;
    private final Map<AppointmentCancelTaskType, Function<AppointmentCancelTaskResponseDto, BatchResponse<AppointmentResponseDto>>> operations;

    public AppointmentCancelTaskOrchestratorImpl(@Qualifier("appointmentBatchCancelNotifyDecorator") AppointmentBatchCancelService appointmentService,
                                                 AppointmentCancelTaskService taskService) {
        this.taskService = taskService;
        this.operations = Map.of(
                AppointmentCancelTaskType.CANCEL_BATCH, (dto) -> appointmentService.cancelBatch(dto.participantId()),
                AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA, (dto) -> appointmentService.cancelBatchByDate(dto.participantId(), dto.date())
        );
    }

    @Transactional
    @Override
    public void cancelBatch(AppointmentCancelTaskType taskType) {
        AppointmentCancelTaskResponseDto dto = taskService.findFirstByType(taskType);
        if (dto != null) {
            var operation = operations.get(dto.type());
            if (operation == null) {
                throw new IllegalStateException("Unexpected AppointmentCancelTaskType");
            }
            BatchResponse<AppointmentResponseDto> batch = operation.apply(dto);
            if (!batch.hasNext()) {
                taskService.deleteById(dto.id());
            }
        }
    }
}
