package com.specialist.schedule.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnsupportedAppointmentCancelTaskTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("appointment_cancel_task_type", "Unsupported code for appointment cancel task type");
    }
}
