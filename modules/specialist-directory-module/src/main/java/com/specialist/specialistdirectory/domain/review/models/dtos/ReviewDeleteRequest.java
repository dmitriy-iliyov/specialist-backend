package com.specialist.specialistdirectory.domain.review.models.dtos;

import java.util.UUID;

public record ReviewDeleteRequest(
        UUID id,
        UUID specialistId,
        UUID creatorId
) {
    public static ReviewDeleteRequest of(UUID id, UUID specialistId, UUID creatorId) {
        return new ReviewDeleteRequest(id, specialistId, creatorId);
    }
}
