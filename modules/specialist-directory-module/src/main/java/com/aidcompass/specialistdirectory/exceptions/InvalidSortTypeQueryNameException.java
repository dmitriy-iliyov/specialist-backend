package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class InvalidSortTypeQueryNameException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("sortBy", "Invalid query name.");
    }
}
