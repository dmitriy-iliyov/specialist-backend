package com.specialist.schedule.interval.services;

import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.NearestIntervalDto;

import java.util.UUID;

public interface NearestIntervalService {
    NearestIntervalDto findBySpecialistId(UUID specialistId);

    void deleteBySpecialistId(UUID specialistId, Long id);

    void replaceIfEarlier(UUID specialistId, IntervalResponseDto dto);
}
