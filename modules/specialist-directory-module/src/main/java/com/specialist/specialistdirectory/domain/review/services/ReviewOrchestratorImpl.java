package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class ReviewOrchestratorImpl implements ReviewOrchestrator {

    private final ReviewService reviewService;
    private final SpecialistRatingService ratingService;
    private final ReviewBufferService reviewBufferService;

    public ReviewOrchestratorImpl(ReviewService reviewService,
                                  @Qualifier("defaultSpecialistRatingServiceDecorator")
                                  SpecialistRatingService ratingService,
                                  ReviewBufferService reviewBufferService) {
        this.reviewService = reviewService;
        this.ratingService = ratingService;
        this.reviewBufferService = reviewBufferService;
    }

    @Transactional
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {
        ReviewResponseDto resultDto = reviewService.save(dto);
        ratingService.updateRatingById(dto.getSpecialistId(), dto.getRating(), OperationType.PERSIST);
        reviewBufferService.put(dto.getCreatorId(), dto.getRating());
        return resultDto;
    }

    @Transactional
    @Override
    public ReviewResponseDto update(ReviewUpdateDto dto) {
        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> resultPair = reviewService.update(dto);
        ReviewResponseDto newDto = resultPair.getRight().get(ReviewAgeType.NEW);
        if (resultPair.getLeft().equals(NextOperationType.UPDATE)) {
            ReviewResponseDto oldDto = resultPair.getRight().get(ReviewAgeType.OLD);
            long resultRating = newDto.rating() - oldDto.rating();
            ratingService.updateRatingById(dto.getSpecialistId(), resultRating, OperationType.UPDATE);
            reviewBufferService.put(dto.getCreatorId(), resultRating);
        }
        return newDto;
    }

    @Transactional
    @Override
    public void delete(UUID creatorId, UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(creatorId, specialistId, id);
        ratingService.updateRatingById(specialistId, -dto.rating(), OperationType.DELETE);
        reviewBufferService.put(creatorId, -dto.rating());
    }

    @Transactional
    @Override
    public void adminDelete(UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(specialistId, id);
        ratingService.updateRatingById(dto.id(), -dto.rating(), OperationType.DELETE);
        reviewBufferService.put(dto.creatorId(), -dto.rating());
    }
}