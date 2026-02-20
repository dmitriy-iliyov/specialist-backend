package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;

public interface ReviewManagementFacade {
    ReviewResponseDto save(ReviewCreateRequest request);

    ReviewResponseDto update(ReviewUpdateRequest request);

    void delete(ReviewDeleteRequest request);
}
