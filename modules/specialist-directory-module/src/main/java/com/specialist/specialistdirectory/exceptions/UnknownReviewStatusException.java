package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnknownReviewStatusException extends BaseBadRequestException {

    private final ErrorDto dto;

    public UnknownReviewStatusException() {
        this.dto = new ErrorDto("status", "Unknown status");
    }

    public UnknownReviewStatusException(String message) {
        this.dto = new ErrorDto("status", message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return dto;
    }
}
