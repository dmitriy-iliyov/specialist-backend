package com.specialist.specialistdirectory.exceptions;


import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;


public class OwnershipException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("authorities", "You do not have enough authorities.");
    }
}
