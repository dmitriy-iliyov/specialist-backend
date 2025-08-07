package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;


public class NotAffiliatedToSpecialistException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("review", "Review not related to specialist.");
    }
}
