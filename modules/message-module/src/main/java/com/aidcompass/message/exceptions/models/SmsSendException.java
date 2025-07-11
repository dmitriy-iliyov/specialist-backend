package com.aidcompass.message.exceptions.models;

import com.aidcompass.core.general.exceptions.models.BaseSendMessageException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class SmsSendException extends BaseSendMessageException {

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
