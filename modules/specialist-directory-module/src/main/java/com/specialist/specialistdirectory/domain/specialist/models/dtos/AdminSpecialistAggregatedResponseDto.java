package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.user.dto.PublicUserResponseDto;

public record AdminSpecialistAggregatedResponseDto(
        PublicUserResponseDto creator,
        FullSpecialistResponseDto specialist
) { }
