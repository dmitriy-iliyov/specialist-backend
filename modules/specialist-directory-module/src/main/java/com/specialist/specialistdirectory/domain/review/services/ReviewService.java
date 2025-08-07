package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.utils.pagination.PageResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto save(ReviewCreateDto dto);

    Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> update(ReviewUpdateDto dto);

    ReviewResponseDto deleteById(UUID creatorId, UUID specialistId, UUID id);

    ReviewResponseDto deleteById(UUID specialistId, UUID id);

    PageResponse<ReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort);
}
