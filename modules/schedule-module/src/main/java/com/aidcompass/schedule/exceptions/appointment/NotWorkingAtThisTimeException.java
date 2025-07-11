package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class NotWorkingAtThisTimeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Can't add appointment because specialist not working at this time!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
