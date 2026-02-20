package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.*;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.review.validation.ReviewValidator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultReviewManagementFacade implements ReviewManagementFacade {

    private final ReviewValidator validator;
    private final ReviewService reviewService;
    private final SystemSpecialistService specialistService;
    private final SpecialistRatingService ratingService;
    private final CreatorRatingUpdateService creatorRatingUpdateService;

    @Transactional
    @Override
    public ReviewResponseDto save(ReviewCreateRequest request) {
        ReviewPayload reviewPayload = validator.validate(request.rawPayload());
        ReviewCreateDto dto = ReviewCreateDto.of(request, reviewPayload);
        ReviewResponseDto resultDto = reviewService.save(specialistService.getReferenceById(dto.specialistId()), dto);
        UUID specialistCreatorId = ratingService.updateRatingById(dto.specialistId(), dto.rating(), OperationType.PERSIST);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, dto.rating(), OperationType.PERSIST);
        return resultDto;
    }

    @Transactional
    @Override
    public ReviewResponseDto update(ReviewUpdateRequest request) {
        ReviewPayload reviewPayload = validator.validate(request.rawPayload());
        ReviewUpdateDto dto = ReviewUpdateDto.of(request, reviewPayload);
        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> resultPair = reviewService.update(dto);
        ReviewResponseDto newDto = resultPair.getRight().get(ReviewAgeType.NEW);
        if (resultPair.getLeft().equals(NextOperationType.UPDATE)) {
            ReviewResponseDto oldDto = resultPair.getRight().get(ReviewAgeType.OLD);
            long resultRating = newDto.rating() - oldDto.rating();
            UUID specialistCreatorId = ratingService.updateRatingById(dto.specialistId(), resultRating, OperationType.UPDATE);
            creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, resultRating, OperationType.UPDATE);
        }
        return newDto;
    }

    @Transactional
    @Override
    public void delete(ReviewDeleteRequest request) {
        ReviewResponseDto dto = reviewService.deleteById(request.creatorId(), request.specialistId(), request.id());
        UUID specialistCreatorId = ratingService.updateRatingById(request.specialistId(), -dto.rating(), OperationType.DELETE);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, -dto.rating(), OperationType.DELETE);
    }
}
