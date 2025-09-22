package com.specialist.schedule.interval.services;

import java.util.List;

public interface SystemIntervalService {
    List<Long> deleteBatchBeforeWeekStart(int batchSize);
}
