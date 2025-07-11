package com.aidcompass.schedule.exceptions.appointment_duration;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class DurationNotFoundByOwnerIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("duration", "Appointment duration not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
