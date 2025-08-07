package com.specialist.specialistdirectory.exceptions;


import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidSortTypeQueryNameException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("sortBy", "Invalid query name.");
    }
}
