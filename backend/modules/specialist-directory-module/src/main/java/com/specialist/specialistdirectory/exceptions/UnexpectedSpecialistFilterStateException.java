package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnexpectedSpecialistFilterStateException extends BaseInternalServerException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("filter", "Unexpected specialist filter state");
    }
}
