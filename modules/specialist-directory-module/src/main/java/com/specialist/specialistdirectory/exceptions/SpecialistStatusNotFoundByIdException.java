package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class SpecialistStatusNotFoundByIdException extends BaseNotFoundException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("status", "Specialist status not found.");
    }
}
