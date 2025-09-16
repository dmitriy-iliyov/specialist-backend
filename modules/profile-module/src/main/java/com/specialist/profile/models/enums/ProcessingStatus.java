package com.specialist.profile.models.enums;

import com.specialist.profile.exceptions.UnknownProcessingStatusException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum ProcessingStatus {
    FAILED(1),
    PROCESSED(2);

    @Getter
    private final int code;

    ProcessingStatus(int code) {
        this.code = code;
    }

    public static ProcessingStatus fromCode(int code) {
        return Arrays.stream(ProcessingStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(() -> {
                    UnknownProcessingStatusException exception = new UnknownProcessingStatusException();
                    log.error(exception.getErrorDto().message());
                    return exception;
                });
    }
}
