package com.aidcompass.auth.domain.account.models.enums;

import com.aidcompass.auth.exceptions.UnknownUnableReasonTypeException;
import lombok.Getter;

import java.util.Arrays;

public enum UnableReasonType {
    EMAIL_CONFIRMATION_REQUIRED(1);

    @Getter
    private final int code;

    UnableReasonType(int code) {
        this.code = code;
    }

    public static UnableReasonType fromCode(int code) {
        return Arrays.stream(UnableReasonType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownUnableReasonTypeException::new);
    }
}
