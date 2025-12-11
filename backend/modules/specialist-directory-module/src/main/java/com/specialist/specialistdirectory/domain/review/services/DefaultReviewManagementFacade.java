package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import io.github.dmitriyiliyov.springoutbox.publisher.OutboxPublisher;
import io.github.dmitriyiliyov.springoutbox.publisher.aop.OutboxPublish;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultReviewManagementFacade implements ReviewManagementFacade {

    private final ReviewService reviewService;
    private final SystemSpecialistService specialistService;
    private final SpecialistRatingService ratingService;
    private final CreatorRatingUpdateService creatorRatingUpdateService;
    private final OutboxPublisher outboxPublisher;

    @Transactional
    @OutboxPublish(eventType = "validate-review")
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {
        ReviewResponseDto resultDto = reviewService.save(specialistService.getReferenceById(dto.getSpecialistId()), dto);
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
            outboxPublisher.publish("validate-review", newDto);
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
