package com.aidcompass.core.contact.exceptions;

import com.aidcompass.core.general.exceptions.models.Exception;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class SendConfirmationMessageException extends Exception {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("sending_message", "Error when sending message!");
    }
}
