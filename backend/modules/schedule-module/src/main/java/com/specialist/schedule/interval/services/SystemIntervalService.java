package com.specialist.schedule.interval.services;

import java.util.List;
import java.util.UUID;

public interface SystemIntervalService {
    void deleteAllFutureBySpecialistId(UUID specialistId);
    List<Long> deleteBatchBeforeWeekStart(int batchSize);
}
