package com.specialist.schedule.interval.services;

import com.specialist.schedule.interval.models.dto.NearestIntervalDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemNearestIntervalService {
    Map<UUID, NearestIntervalDto> findAll(Set<UUID> specialistIds);
}
