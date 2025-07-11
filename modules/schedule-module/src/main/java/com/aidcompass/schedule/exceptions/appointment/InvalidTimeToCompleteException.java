package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class InvalidTimeToCompleteException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Future or past appointment can't be complete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
