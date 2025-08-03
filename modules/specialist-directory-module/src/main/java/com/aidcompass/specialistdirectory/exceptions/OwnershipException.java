package com.aidcompass.specialistdirectory.exceptions;


import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;


public class OwnershipException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("authorities", "You do not have enough authorities.");
    }
}
