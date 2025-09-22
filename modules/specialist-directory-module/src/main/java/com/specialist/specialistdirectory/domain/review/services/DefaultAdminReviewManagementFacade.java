package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DefaultAdminReviewFacade implements AdminReviewFacade {

    private final ReviewService reviewService;
    private final SpecialistRatingService ratingService;
    private final CreatorRatingUpdateService creatorRatingUpdateService;

    public DefaultAdminReviewFacade(ReviewService reviewService, SpecialistRatingService ratingService,
                                    CreatorRatingUpdateService creatorRatingUpdateService) {
        this.reviewService = reviewService;
        this.ratingService = ratingService;
        this.creatorRatingUpdateService = creatorRatingUpdateService;
    }

    @Transactional
    @Override
    public void delete(UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(specialistId, id);
        UUID specialistCreatorId = ratingService.updateRatingById(dto.id(), -dto.rating(), OperationType.DELETE);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, -dto.rating(), OperationType.DELETE);
    }
}
