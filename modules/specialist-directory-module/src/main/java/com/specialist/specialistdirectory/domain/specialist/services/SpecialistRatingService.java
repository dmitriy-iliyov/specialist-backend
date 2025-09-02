package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;

import java.util.UUID;

public interface SpecialistRatingService {
    void updateRatingById(UUID id, long earnedRating, OperationType operationType);
}
