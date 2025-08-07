package com.specialist.specialistdirectory.exceptions;


import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class SpecialistTypeEntityNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("type", "Type not found by id.");
    }
}
