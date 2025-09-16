package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdminReviewFacadeImpl implements AdminReviewFacade {

    private final ReviewService reviewService;
    private final SpecialistRatingService ratingService;
    private final ReviewBufferService reviewBufferService;

    public AdminReviewFacadeImpl(ReviewService reviewService,
                                 @Qualifier("specialistRatingRetryDecorator")
                                  SpecialistRatingService ratingService,
                                 ReviewBufferService reviewBufferService) {
        this.reviewService = reviewService;
        this.ratingService = ratingService;
        this.reviewBufferService = reviewBufferService;
    }

    @Transactional
    @Override
    public void delete(UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(specialistId, id);
        UUID specialistCreatorId = ratingService.updateRatingById(dto.id(), -dto.rating(), OperationType.DELETE);
        reviewBufferService.put(specialistCreatorId, -dto.rating());
    }
}
