package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class NotAffiliatedToSpecialistException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("review", "Review not related to specialist.");
    }
}
