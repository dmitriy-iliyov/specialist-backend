package com.specialist.specialistdirectory.domain.review.models.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ReviewCreateRequest(
        UUID creatorId,
        UUID specialistId,
        String rawPayload,
        MultipartFile picture
) {
    public static ReviewCreateRequest of(UUID creatorId, UUID specialistId, String rawPayload, MultipartFile picture) {
        return new ReviewCreateRequest(creatorId, specialistId, rawPayload, picture);
    }
}
