package com.aidcompass.users.general.exceptions.customer;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class CustomerNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("customer", "Customer not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
