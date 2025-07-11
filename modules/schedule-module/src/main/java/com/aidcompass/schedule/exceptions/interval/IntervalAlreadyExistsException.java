package com.aidcompass.schedule.exceptions.interval;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class IntervalAlreadyExistsException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Interval all ready exist!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
