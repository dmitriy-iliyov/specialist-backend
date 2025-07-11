package com.aidcompass.core.contact.exceptions.not_found;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class ContactProgressEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("progress_entity", "Not found by user id!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
