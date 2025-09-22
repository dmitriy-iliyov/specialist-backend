package com.specialist.schedule.interval.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class IntervalTaskScheduler {

    @Value("${appointment.tasks.batch-size}")
    private int batchSize;
    private final SystemIntervalService service;

    @Scheduled(cron = "0 */10 4 * * 6")
    public void cleanBeforeWeekStart() {
        service.deleteBatchBeforeWeekStart(batchSize);
    }
}