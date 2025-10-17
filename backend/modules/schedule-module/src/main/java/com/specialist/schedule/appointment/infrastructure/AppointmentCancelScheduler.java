package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentCancelScheduler {

    private final AppointmentBatchCancelService appointmentService;
    private final AppointmentCancelTaskService taskService;

    public AppointmentCancelScheduler(@Qualifier("appointmentBatchCancelNotifyDecorator") AppointmentBatchCancelService appointmentService,
                                      AppointmentCancelTaskService taskService) {
        this.appointmentService = appointmentService;
        this.taskService = taskService;
    }

    @Scheduled(
            initialDelayString = "${api.appointments.clean-batch-by-date.initial_delay}",
            fixedDelayString = "${api.appointments.clean-batch-by-date.fixed_delay}"
    )
    @Transactional
    public void cancelBatchByDate() {
        AppointmentCancelTaskResponseDto dto = taskService.findFirstByType(AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA);
        process(dto);
    }

    @Scheduled(
            initialDelayString = "${api.appointments.clean-batch.initial_delay}",
            fixedDelayString = "${api.appointments.clean-batch.fixed_delay}"
    )
    @Transactional
    public void cancelBatch() {
        AppointmentCancelTaskResponseDto dto = taskService.findFirstByType(AppointmentCancelTaskType.CANCEL_BATCH);
        process(dto);
    }

    private void process(AppointmentCancelTaskResponseDto dto) {
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
