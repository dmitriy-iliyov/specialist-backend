package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.contracts.schedule.NearestIntervalDto;

public record SpecialistAggregatedResponseDto(
        UnifiedProfileResponseDto creator,
        SpecialistResponseDto specialist,
        @JsonProperty("nearest_interval")
        NearestIntervalDto nearestInterval
) { }
