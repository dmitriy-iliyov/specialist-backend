package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnableSpecialistManageException extends BaseBadRequestException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("action", "Unable specialist manage exception");
    }
}
