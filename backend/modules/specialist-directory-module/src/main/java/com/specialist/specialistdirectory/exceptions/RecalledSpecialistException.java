package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseForbiddenException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class RecalledSpecialistException extends BaseForbiddenException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("specialist", "You haven't authorities to update because specialist is recall.");
    }
}
