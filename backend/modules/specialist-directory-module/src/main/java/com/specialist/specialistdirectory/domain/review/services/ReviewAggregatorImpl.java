package com.specialist.specialistdirectory.domain.review.services;


import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
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
    private final SystemProfileService profileService;

    public ReviewAggregatorImpl(ReviewService reviewService,
                                @Qualifier("defaultSystemProfileService") SystemProfileService profileService) {
        this.reviewService = reviewService;
        this.profileService = profileService;
    }

    @Cacheable(value = "reviews", key = "#specialistId + ':' + #sort.cacheKey()")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<ReviewAggregatedResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort) {
        PageResponse<ReviewResponseDto> reviewsPage = reviewService.findAllWithSortBySpecialistIdAndStatus(
                specialistId, ReviewStatus.APPROVED, sort
        );
        Set<UUID> creatorIds = reviewsPage.data().stream().map(ReviewResponseDto::creatorId).collect(Collectors.toSet());
        Map<UUID, UnifiedProfileResponseDto> creatorsMap = profileService.findAllByIdIn(creatorIds);
        return new PageResponse<>(
                reviewsPage.data().stream()
                        .map(review -> new ReviewAggregatedResponseDto(creatorsMap.get(review.creatorId()), review))
                        .toList(),
                reviewsPage.totalPages()
        );
    }
}
