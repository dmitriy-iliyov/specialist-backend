package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultAdminReviewManagementFacade implements AdminReviewManagementFacade {

    private final ReviewService reviewService;
    private final SpecialistRatingService ratingService;
    private final CreatorRatingUpdateService creatorRatingUpdateService;

    @Transactional
    @Override
    public void delete(UUID specialistId, UUID id) {
        ReviewResponseDto dto = reviewService.deleteById(specialistId, id);
        UUID specialistCreatorId = ratingService.updateRatingById(dto.id(), -dto.rating(), OperationType.DELETE);
        creatorRatingUpdateService.updateByCreatorId(specialistCreatorId, -dto.rating(), OperationType.DELETE);
    }
}
