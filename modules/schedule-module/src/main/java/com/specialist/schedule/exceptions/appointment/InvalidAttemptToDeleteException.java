package com.specialist.schedule.exceptions.appointment;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidAttemptToDeleteException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Completed appointment can't be delete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
