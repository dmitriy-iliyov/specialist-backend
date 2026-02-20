package com.specialist.specialistdirectory.domain.review.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.specialistdirectory.exceptions.UnknownReviewStatusException;

public enum ReviewStatus {
    UNAPPROVED,
    APPROVED;

    @JsonCreator
    public static ReviewStatus fromString(String value) {
        if (value == null) {
            throw new UnknownReviewStatusException("Status cannot be null");
        }
        if (value.isBlank()) {
            throw new UnknownReviewStatusException("Status cannot be blank ");
        }
        try {
            return ReviewStatus.valueOf(value);
        } catch (Exception e) {
            throw new UnknownReviewStatusException();
        }
    }
}
