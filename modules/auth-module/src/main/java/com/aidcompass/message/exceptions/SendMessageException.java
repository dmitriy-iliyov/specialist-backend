package com.aidcompass.message.exceptions;

import com.aidcompass.core.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class SendMessageException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("message", "Sending failed.");
    }
}
