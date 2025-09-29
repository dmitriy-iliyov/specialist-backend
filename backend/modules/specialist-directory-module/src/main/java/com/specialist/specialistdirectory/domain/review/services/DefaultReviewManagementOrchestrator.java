package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultReviewManagementOrchestrator implements ReviewManagementOrchestrator {

    private final ReviewService reviewService;
    private final SpecialistRatingService ratingService;
    private final CreatorRatingUpdateService creatorRatingUpdateService;

    @Transactional
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {
        ReviewResponseDto resultDto = reviewService.save(dto);
        UUID specialistCreatorId = ratingService.updateRatingById(dto.getSpecialistId(), dto.getRating(), OperationType.PERSIST);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, dto.getRating(), OperationType.PERSIST);
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
            UUID specialistCreatorId = ratingService.updateRatingById(dto.getSpecialistId(), resultRating, OperationType.UPDATE);
            creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, resultRating, OperationType.UPDATE);
        }
        return newDto;
    }

    @Transactional
    @Override
    public void delete(UUID creatorId, UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(creatorId, specialistId, id);
        UUID specialistCreatorId = ratingService.updateRatingById(specialistId, -dto.rating(), OperationType.DELETE);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, -dto.rating(), OperationType.DELETE);
    }
}
