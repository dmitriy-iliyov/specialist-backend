package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class SpecialistTypeEntityNotFoundByTitleException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("specialist_type", "Specialist type not found by type!");
    }
}
