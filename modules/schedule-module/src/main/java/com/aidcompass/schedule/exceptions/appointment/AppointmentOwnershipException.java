package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class AppointmentOwnershipException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "This is not your appointment!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
