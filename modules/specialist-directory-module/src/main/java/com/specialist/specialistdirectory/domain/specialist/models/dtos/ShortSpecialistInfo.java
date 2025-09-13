package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;

import java.util.UUID;

public record ShortSpecialistInfo(
        UUID creatorId,
        UUID ownerId,
        SpecialistStatus status
) { }
