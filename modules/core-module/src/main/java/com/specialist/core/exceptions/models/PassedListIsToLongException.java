package com.specialist.core.exceptions.models;


import com.specialist.core.exceptions.models.dto.ErrorDto;

public class PassedListIsToLongException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("ids", "List of id shouldn't have more then 10 elements!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
