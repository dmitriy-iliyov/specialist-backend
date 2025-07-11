package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class AppointmentNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Appointment not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
