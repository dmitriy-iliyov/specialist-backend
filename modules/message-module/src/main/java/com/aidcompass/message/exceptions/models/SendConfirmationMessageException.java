package com.aidcompass.message.exceptions.models;

import com.aidcompass.core.exceptions.models.BaseSendMessageException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class SendConfirmationMessageException extends BaseSendMessageException {

    private final ErrorDto errorDto = new ErrorDto("sending_message", "Error when sending message!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
