package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.user.PublicUserResponseDto;

public record SpecialistAggregatedResponseDto(
        PublicUserResponseDto creator,
        SpecialistResponseDto specialist
) { }
