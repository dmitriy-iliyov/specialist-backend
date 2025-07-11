package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;

import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto save(ReviewCreateDto dto);

    ReviewResponseDto update(ReviewUpdateDto dto);

    void delete(UUID creatorId, UUID specialistId, UUID id);
}
