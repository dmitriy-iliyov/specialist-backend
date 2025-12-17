package com.specialist.specialistdirectory.domain.review.models.dtos;

import java.util.UUID;

public record ReviewUpdateDto(
        UUID id,
        UUID specialistId,
        UUID creatorId,
        String description,
        Integer rating
) {

    public static ReviewUpdateDto of(ReviewUpdateRequest request, ReviewPayload payload) {
        return new ReviewUpdateDto(request.id(), request.specialistId(), request.creatorId(), payload.description(), payload.rating());
    }
}