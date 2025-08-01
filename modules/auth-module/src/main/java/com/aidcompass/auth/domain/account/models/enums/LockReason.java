package com.aidcompass.auth.domain.account.models.enums;

import com.aidcompass.auth.exceptions.UnknownLockReasonTypeException;
import lombok.Getter;

import java.util.Arrays;

public enum LockReason {
    SPAM(1);

    @Getter
    private final int code;

    LockReason(int code) {
        this.code = code;
    }

    public static LockReason fromCode(int code) {
        return Arrays.stream(LockReason.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownLockReasonTypeException::new);
    }
}
