package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AppointmentCancelScheduler {

    private final AppointmentCancelTaskOrchestrator orchestrator;

    @Scheduled(
            initialDelayString = "${api.appointments.clean-batch-by-date.initial_delay}",
            fixedDelayString = "${api.appointments.clean-batch-by-date.fixed_delay}"
    )
    @Transactional
    public void cancelBatchByDate() {
        orchestrator.cancelBatch(AppointmentCancelTaskType.CANCEL_BATCH_BY_DATA);
    }

    @Scheduled(
            initialDelayString = "${api.appointments.clean-batch.initial_delay}",
            fixedDelayString = "${api.appointments.clean-batch.fixed_delay}"
    )
    @Transactional
    public void cancelBatch() {
        orchestrator.cancelBatch(AppointmentCancelTaskType.CANCEL_BATCH);
    }
}
