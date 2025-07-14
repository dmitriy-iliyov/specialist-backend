package com.aidcompass.specialistdirectory.domain.review.services.interfases;

import com.aidcompass.specialistdirectory.domain.review.models.NextOperation;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto save(ReviewCreateDto dto);

    Pair<NextOperation, List<ReviewResponseDto>> update(ReviewUpdateDto dto);

    ReviewResponseDto deleteByCreatorIdAndId(UUID creatorId, UUID id);
}
