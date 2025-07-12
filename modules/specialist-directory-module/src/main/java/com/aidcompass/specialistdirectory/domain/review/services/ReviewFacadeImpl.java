package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReviewFacadeImpl implements ReviewFacade {
    @Override
    public void delete(UUID creatorId, UUID id) {

    }

    @Override
    public ReviewResponseDto update(ReviewUpdateDto dto) {
        return null;
    }
}
