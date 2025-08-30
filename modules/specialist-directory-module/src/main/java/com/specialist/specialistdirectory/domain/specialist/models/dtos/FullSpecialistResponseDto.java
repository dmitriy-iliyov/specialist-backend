package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.user.PublicUserResponseDto;

public record FullSpecialistResponseDto(
        PublicUserResponseDto creator,
        SpecialistResponseDto specialist
) { }
