package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;


public class SpecialistCreatorIdNotFoundByIdException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("creator_id", "Specialist user id not found by specialist id.");
    }
}
