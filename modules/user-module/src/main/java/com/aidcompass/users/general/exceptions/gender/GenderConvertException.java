package com.aidcompass.users.general.exceptions.gender;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class GenderConvertException extends BaseInvalidInputException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Convert exception!");
    }
}
