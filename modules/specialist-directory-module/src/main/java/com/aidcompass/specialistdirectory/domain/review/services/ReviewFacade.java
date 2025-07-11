package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;

import java.util.UUID;

public interface ReviewFacade {
    void delete(UUID creatorId, UUID id);

    ReviewResponseDto update(ReviewUpdateDto dto);

}
