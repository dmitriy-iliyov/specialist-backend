package com.aidcompass.schedule.exceptions.appointment;

import com.aidcompass.core.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class AppointmentTypeConvertException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("type", "Convert exception!");
    }
}
