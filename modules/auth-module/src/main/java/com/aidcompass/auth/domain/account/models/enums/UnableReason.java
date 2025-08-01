package com.aidcompass.auth.domain.account.models.enums;

import com.aidcompass.auth.exceptions.UnknownUnableReasonTypeException;
import lombok.Getter;

import java.util.Arrays;

public enum UnableReason {
    EMAIL_CONFIRMATION_REQUIRED(1);

    @Getter
    private final int code;

    UnableReason(int code) {
        this.code = code;
    }

    public static UnableReason fromCode(int code) {
        return Arrays.stream(UnableReason.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownUnableReasonTypeException::new);
    }
}
