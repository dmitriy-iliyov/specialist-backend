package com.aidcompass.schedule.exceptions.interval;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class IntervalTimeIsInvalidException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Non-working hours!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
