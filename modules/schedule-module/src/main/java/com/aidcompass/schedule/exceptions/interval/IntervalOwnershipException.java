package com.aidcompass.schedule.exceptions.interval;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class IntervalOwnershipException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Ownership exception!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
