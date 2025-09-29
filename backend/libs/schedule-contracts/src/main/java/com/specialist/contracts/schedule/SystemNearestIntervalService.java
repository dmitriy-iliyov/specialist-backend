package com.specialist.contracts.schedule;


import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemNearestIntervalService {
    Map<UUID, NearestIntervalDto> findAll(Set<UUID> specialistIds);
}
