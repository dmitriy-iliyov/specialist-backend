package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class AppointmentAlreadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "You already have appointment at this time!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
