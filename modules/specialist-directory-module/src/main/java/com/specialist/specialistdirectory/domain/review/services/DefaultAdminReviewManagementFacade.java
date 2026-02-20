package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultAdminReviewManagementFacade implements AdminReviewManagementFacade {

    private final ReviewService reviewService;
    private final SpecialistRatingService ratingService;
    private final CreatorRatingUpdateService creatorRatingUpdateService;
    private final SystemProfileService profileService;

    public DefaultAdminReviewManagementFacade(ReviewService reviewService,
                                              SpecialistRatingService ratingService,
                                              CreatorRatingUpdateService creatorRatingUpdateService,
                                              @Qualifier("defaultSystemProfileService") SystemProfileService profileService) {
        this.reviewService = reviewService;
        this.ratingService = ratingService;
        this.creatorRatingUpdateService = creatorRatingUpdateService;
        this.profileService = profileService;
    }

    @Override
    public PageResponse<ReviewAggregatedResponseDto> getAll(UUID specialistId, AdminReviewSort sort) {
        PageResponse<ReviewResponseDto> reviewsPage = reviewService.findAllWithSortBySpecialistIdAndStatus(
                specialistId,
                sort.getStatus(),
                sort
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

    @Override
    public void approve(UUID specialistId, UUID id) {
        reviewService.approve(specialistId, id, ApproverType.ADMIN);
    }

    @Transactional
    @Override
    public void delete(UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(specialistId, id);
        UUID specialistCreatorId = ratingService.updateRatingById(dto.id(), -dto.rating(), OperationType.DELETE);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, -dto.rating(), OperationType.DELETE);
    }
}
