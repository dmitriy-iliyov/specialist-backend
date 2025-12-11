package com.specialist.schedule.appointment.infrastructure;

import com.specialist.schedule.appointment.services.SystemAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentSkipScheduler {

    @Value("${api.appointment.tasks.batch-size}")
    private int BATCH_SIZE;
    private final SystemAppointmentService service;

    @Scheduled(cron = "0 */2 2 * * *")
    public void skip() {
        service.skipBatch(BATCH_SIZE);
    }
}