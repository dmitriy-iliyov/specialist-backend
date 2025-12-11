package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentCancelTaskOrchestratorImpl implements AppointmentCancelTaskOrchestrator {

    private final AppointmentBatchCancelService appointmentService;
    private final AppointmentCancelTaskService taskService;

    public AppointmentCancelTaskOrchestratorImpl(@Qualifier("appointmentBatchCancelNotifyDecorator")
                                                 AppointmentBatchCancelService appointmentService,
                                                 AppointmentCancelTaskService taskService) {
        this.appointmentService = appointmentService;
        this.taskService = taskService;
    }

    @Transactional
    @Override
    public void cancelBatch(AppointmentCancelTaskType taskType) {
        AppointmentCancelTaskResponseDto dto = taskService.findFirstByType(taskType);
        if (dto != null) {
            BatchResponse<AppointmentResponseDto> batch = switch (dto.type()) {
                case CANCEL_BATCH -> appointmentService.cancelBatch(dto.participantId());
                case CANCEL_BATCH_BY_DATA -> appointmentService.cancelBatchByDate(dto.participantId(), dto.date());
            };
            if (!batch.hasNext()) {
                taskService.deleteById(dto.id());
            }
        }
    }
}
