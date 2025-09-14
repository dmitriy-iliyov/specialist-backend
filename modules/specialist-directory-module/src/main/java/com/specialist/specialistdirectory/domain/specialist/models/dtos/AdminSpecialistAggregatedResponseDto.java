package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.user.dto.UnifiedProfileResponseDto;

public record AdminSpecialistAggregatedResponseDto(
        UnifiedProfileResponseDto creator,
        FullSpecialistResponseDto specialist
) { }
