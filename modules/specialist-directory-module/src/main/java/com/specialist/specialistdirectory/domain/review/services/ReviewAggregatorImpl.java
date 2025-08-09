package com.specialist.specialistdirectory.domain.review.services;


import com.specialist.contracts.user.PublicUserResponseDto;
import com.specialist.contracts.user.SystemUserService;
import com.specialist.specialistdirectory.domain.review.models.dtos.FullReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewAggregatorImpl implements ReviewAggregator {

    private final ReviewService reviewService;
    private final SystemUserService systemUserService;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort) {
        PageResponse<ReviewResponseDto> reviewsPage = reviewService.findAllWithSortBySpecialistId(specialistId, sort);
        Set<UUID> userIds = reviewsPage.data().stream().map(ReviewResponseDto::creatorId).collect(Collectors.toSet());
        Map<UUID, PublicUserResponseDto> userMap = systemUserService.findAllByIdIn(userIds);
        return new PageResponse<>(
                reviewsPage.data().stream()
                        .map(review -> new FullReviewResponseDto(userMap.get(review.creatorId()), review))
                        .toList(),
                reviewsPage.totalPages()
        );
    }
}
