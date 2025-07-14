package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.NextOperation;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.domain.review.services.interfases.ReviewOrchestrator;
import com.aidcompass.specialistdirectory.domain.review.services.interfases.ReviewService;
import com.aidcompass.specialistdirectory.domain.specialist.services.interfaces.SpecialistOrchestrator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewOrchestratorImpl implements ReviewOrchestrator {

    private final ReviewService reviewService;
    private final SpecialistOrchestrator specialistOrchestrator;


    @Transactional
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {
        ReviewResponseDto responseDto = reviewService.save(dto);
        specialistOrchestrator.updateRatingById(dto.getSpecialistId(), dto.getRating());
        return responseDto;
    }

    @Transactional
    @Override
    public ReviewResponseDto update(ReviewUpdateDto dto) {
        Pair<NextOperation, List<ReviewResponseDto>> resultPair = reviewService.update(dto);
        ReviewResponseDto newDto = resultPair.getRight().get(1);
        if (resultPair.getLeft().equals(NextOperation.UPDATE_RATING)) {
            ReviewResponseDto oldDto = resultPair.getRight().get(0);
            long resultRating = newDto.rating() - oldDto.rating();
            specialistOrchestrator.updateRatingById(dto.getSpecialistId(), resultRating);
        }
        return newDto;
    }

    @Transactional
    @Override
    public void delete(UUID creatorId, UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteByCreatorIdAndId(creatorId, id);
        specialistOrchestrator.updateRatingById(dto.id(), -dto.rating());
    }

    private void assertOwnerShip() {}
}
