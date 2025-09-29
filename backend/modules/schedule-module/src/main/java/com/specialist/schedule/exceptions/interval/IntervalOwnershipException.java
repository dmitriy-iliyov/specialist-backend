package com.specialist.schedule.exceptions.interval;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class IntervalOwnershipException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Ownership exception!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
