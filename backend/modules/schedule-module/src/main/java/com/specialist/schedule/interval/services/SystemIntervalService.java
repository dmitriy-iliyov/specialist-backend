package com.specialist.schedule.interval.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SystemIntervalService {
    void deleteAllFutureBySpecialistId(UUID specialistId);

    void deleteAllFutureBySpecialistIds(Set<UUID> specialistIds);

    List<Long> deleteBatchBeforeWeekStart(int batchSize);
}
