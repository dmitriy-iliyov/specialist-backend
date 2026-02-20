package com.specialist.specialistdirectory.domain.review.validation;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewPayload;

public interface ReviewValidator {
    ReviewPayload validate(String rawPayload);
}
