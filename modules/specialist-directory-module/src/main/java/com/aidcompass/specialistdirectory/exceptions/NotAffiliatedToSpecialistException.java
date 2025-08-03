package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;


public class NotAffiliatedToSpecialistException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("review", "Review not related to specialist.");
    }
}
