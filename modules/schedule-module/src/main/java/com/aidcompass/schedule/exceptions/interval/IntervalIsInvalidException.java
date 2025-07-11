package com.aidcompass.schedule.exceptions.interval;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class IntervalIsInvalidException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Start of work interval is after end!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
