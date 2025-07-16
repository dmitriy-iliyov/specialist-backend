package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class ReviewNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("review", "Not found by id.");
    }
}
