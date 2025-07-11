package com.aidcompass.message.exceptions.models;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class SendMessageException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
