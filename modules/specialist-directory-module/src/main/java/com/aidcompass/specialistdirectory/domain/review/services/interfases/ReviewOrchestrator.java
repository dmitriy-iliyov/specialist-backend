package com.aidcompass.specialistdirectory.domain.review.services.interfases;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ReviewOrchestrator {
    ReviewResponseDto save(ReviewCreateDto dto);

    ReviewResponseDto update(ReviewUpdateDto dto);

    void delete(UUID creatorId, UUID specialistId, UUID id);
}
