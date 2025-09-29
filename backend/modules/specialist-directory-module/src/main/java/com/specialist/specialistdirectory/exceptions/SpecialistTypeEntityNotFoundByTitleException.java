package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;


public class SpecialistTypeEntityNotFoundByTitleException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("specialist_type", "Specialist type not found by type!");
    }
}
