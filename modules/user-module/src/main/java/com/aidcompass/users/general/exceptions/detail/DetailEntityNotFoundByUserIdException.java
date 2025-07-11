package com.aidcompass.users.general.exceptions.detail;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class DetailEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("detail", "Detail not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
