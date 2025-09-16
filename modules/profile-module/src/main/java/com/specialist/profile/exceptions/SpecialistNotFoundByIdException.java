package com.specialist.profile.exceptions;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class SpecialistNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("specialist", "Specialist not found.");
    }
}
