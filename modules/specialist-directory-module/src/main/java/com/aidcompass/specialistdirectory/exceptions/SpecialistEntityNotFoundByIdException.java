package com.aidcompass.specialistdirectory.exceptions;


import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class SpecialistEntityNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("specialist", "Specialist not found by id.");
    }
}
