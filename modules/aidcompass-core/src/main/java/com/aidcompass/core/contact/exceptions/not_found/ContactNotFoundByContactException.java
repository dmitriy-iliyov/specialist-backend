package com.aidcompass.core.contact.exceptions.not_found;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class ContactNotFoundByContactException extends BaseNotFoundException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
