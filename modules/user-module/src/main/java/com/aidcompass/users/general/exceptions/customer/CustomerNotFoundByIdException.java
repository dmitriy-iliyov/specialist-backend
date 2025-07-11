package com.aidcompass.users.general.exceptions.customer;


import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class CustomerNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("customer", "Customer not found");


    public CustomerNotFoundByIdException() {}


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
