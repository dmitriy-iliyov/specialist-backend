package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;

import java.util.UUID;

public interface ReviewOrchestrator {
    ReviewResponseDto save(ReviewCreateDto dto);

    ReviewResponseDto update(ReviewUpdateDto dto);

    void delete(UUID creatorId, UUID specialistId, UUID id);

    void adminDelete(UUID specialistId, UUID id);
}
