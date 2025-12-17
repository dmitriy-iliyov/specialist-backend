package com.specialist.specialistdirectory.domain.review.models.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ReviewUpdateRequest(
        UUID id,
        UUID creatorId,
        UUID specialistId,
        String rawPayload,
        MultipartFile picture
) {
    public static ReviewUpdateRequest of(UUID id, UUID creatorId, UUID specialistId, String rawPayload, MultipartFile picture) {
        return new ReviewUpdateRequest(id, creatorId, specialistId, rawPayload, picture);
    }
}
