package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.user.dto.PublicUserResponseDto;

public record SpecialistAggregatedResponseDto(
        PublicUserResponseDto creator,
        SpecialistResponseDto specialist
) { }
