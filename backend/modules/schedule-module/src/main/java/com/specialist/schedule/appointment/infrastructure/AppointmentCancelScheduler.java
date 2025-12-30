package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentCancelScheduler {

    @Value("${api.appointment.tasks.cancel.batch-size}")
    public int BY_DATE_BATCH_SIZE;

    @Value("${api.appointment.tasks.cancel.batch-size}")
    public int BATCH_SIZE;

    private final AppointmentCancelTaskOrchestrator orchestrator;

    @Scheduled(
            initialDelayString = "${api.appointments.clean-batch-by-date.initial_delay}",
            fixedDelayString = "${api.appointments.clean-batch-by-date.fixed_delay}"
    )
    public void cancelBatchByDate() {
        orchestrator.cancelBatch(AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA, BY_DATE_BATCH_SIZE);
    }

    @Scheduled(
            initialDelayString = "${api.appointments.clean-batch.initial_delay}",
            fixedDelayString = "${api.appointments.clean-batch.fixed_delay}"
    )
    public void cancelBatch() {
        orchestrator.cancelBatch(AppointmentCancelTaskType.CANCEL_BATCH, BATCH_SIZE);
    }
}
