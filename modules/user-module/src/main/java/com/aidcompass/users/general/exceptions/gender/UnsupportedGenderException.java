package com.aidcompass.users.general.exceptions.gender;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class UnsupportedGenderException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("gender", "Unsupported gender!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
