package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class UnknownDeliveryStateException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("kafka-event", "Unknown delivery state.");
    }
}
