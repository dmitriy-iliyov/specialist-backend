package com.aidcompass.specialistdirectory.domain.review.models.dtos;

import java.util.Set;
import java.util.UUID;

public record DeleteRequestDto(
        Set<UUID> ids
) { }
