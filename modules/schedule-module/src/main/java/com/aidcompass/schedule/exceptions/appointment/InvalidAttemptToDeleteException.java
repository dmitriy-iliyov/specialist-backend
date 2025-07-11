package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class InvalidAttemptToDeleteException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Completed appointment can't be delete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
