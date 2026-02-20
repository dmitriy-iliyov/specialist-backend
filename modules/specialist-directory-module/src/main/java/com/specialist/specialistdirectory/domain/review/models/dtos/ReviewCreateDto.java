package com.specialist.specialistdirectory.domain.review.models.dtos;

import java.util.UUID;

public record ReviewCreateDto(
        UUID creatorId,
        UUID specialistId,
        String description,
        Integer rating
) {

    public static ReviewCreateDto of(ReviewCreateRequest request, ReviewPayload payload) {
        return new ReviewCreateDto(request.creatorId(), request.specialistId(), payload.description(), payload.rating());
    }
}
