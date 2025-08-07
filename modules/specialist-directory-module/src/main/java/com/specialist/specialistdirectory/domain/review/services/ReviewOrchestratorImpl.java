package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistOrchestrator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewOrchestratorImpl implements ReviewOrchestrator {

    private final ReviewService reviewService;
    private final SpecialistOrchestrator specialistOrchestrator;
    private final ReviewBufferService reviewBufferService;


    @Transactional
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {
        ReviewResponseDto resultDto = reviewService.save(dto);
        specialistOrchestrator.updateRatingById(dto.getSpecialistId(), dto.getRating(), OperationType.PERSIST);
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
            specialistOrchestrator.updateRatingById(dto.getSpecialistId(), resultRating, OperationType.UPDATE);
            reviewBufferService.put(dto.getCreatorId(), resultRating);
        }
        return newDto;
    }

    @Transactional
    @Override
    public void delete(UUID creatorId, UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(creatorId, specialistId, id);
        specialistOrchestrator.updateRatingById(specialistId, -dto.rating(), OperationType.DELETE);
        reviewBufferService.put(creatorId, -dto.rating());
    }

    @Transactional
    @Override
    public void adminDelete(UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(specialistId, id);
        specialistOrchestrator.updateRatingById(dto.id(), -dto.rating(), OperationType.DELETE);
        reviewBufferService.put(dto.creatorId(), -dto.rating());
    }
}