package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnexpectedNonManagedSpecialistException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("error", "Unexpected error.");
    }
}
