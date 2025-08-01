package com.aidcompass.auth.domain.account.models.enums;

import com.aidcompass.auth.exceptions.UnknownLockReasonTypeException;
import lombok.Getter;

import java.util.Arrays;

public enum LockReasonType {
    SPAM(1);

    @Getter
    private final int code;

    LockReasonType(int code) {
        this.code = code;
    }

    public static LockReasonType fromCode(int code) {
        return Arrays.stream(LockReasonType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownLockReasonTypeException::new);
    }
}
