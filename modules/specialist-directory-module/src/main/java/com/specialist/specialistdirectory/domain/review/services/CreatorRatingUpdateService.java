package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;

import java.util.UUID;

public interface CreatorRatingUpdateService {
    void updateByCreatorId(UUID creatorId, long rating, OperationType type);
}
