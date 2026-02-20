package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;

public record AdminSpecialistAggregatedResponseDto(
        UnifiedProfileResponseDto creator,
        FullSpecialistResponseDto specialist
) { }
