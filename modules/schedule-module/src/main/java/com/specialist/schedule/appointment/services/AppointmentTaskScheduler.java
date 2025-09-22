package com.specialist.schedule.appointment.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class AppointmentTaskScheduler {

    @Value("${appointment.tasks.batch-size}")
    private int batchSize;
    private final SystemAppointmentService service;

    @Scheduled(cron = "0 */2 2 * * *")
    public void markAsSkipped() {
        service.markBatchAsSkipped(batchSize);
    }
}