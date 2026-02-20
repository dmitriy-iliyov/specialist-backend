package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.utils.pagination.PageResponse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto save(SpecialistEntity specialist, ReviewCreateDto dto);

    void updatePictureUrlById(UUID id, String pictureUrl);

    void approve(UUID specialistId, UUID id, ApproverType approver);

    Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> update(ReviewUpdateDto dto);

    ReviewResponseDto deleteById(UUID creatorId, UUID specialistId, UUID id);

    ReviewResponseDto deleteById(UUID specialistId, UUID id);

    PageResponse<ReviewResponseDto> findAllWithSortBySpecialistIdAndStatus(UUID specialistId, ReviewStatus status, ReviewSort sort);
}
