package com.specialist.specialistdirectory.domain.review.services;


import com.specialist.contracts.profile.SystemProfileReadService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewAggregatorImpl implements ReviewAggregator {

    private final ReviewService reviewService;
    private final SystemProfileReadService profileReadService;

    public ReviewAggregatorImpl(ReviewService reviewService,
                                @Qualifier("defaultSystemProfileReadService") SystemProfileReadService profileReadService) {
        this.reviewService = reviewService;
        this.profileReadService = profileReadService;
    }

    // WARNING: @Transactional till SystemProfileReadService in the same app context
    @Cacheable(value = "reviews", key = "#specialistId + ':' + #sort.cacheKey()")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<ReviewAggregatedResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort) {
        PageResponse<ReviewResponseDto> reviewsPage = reviewService.findAllWithSortBySpecialistId(specialistId, sort);
        Set<UUID> creatorIds = reviewsPage.data().stream().map(ReviewResponseDto::creatorId).collect(Collectors.toSet());
        Map<UUID, UnifiedProfileResponseDto> creatorsMap = profileReadService.findAllByIdIn(creatorIds);
        return new PageResponse<>(
                reviewsPage.data().stream()
                        .map(review -> new ReviewAggregatedResponseDto(creatorsMap.get(review.creatorId()), review))
                        .toList(),
                reviewsPage.totalPages()
        );
    }
}
