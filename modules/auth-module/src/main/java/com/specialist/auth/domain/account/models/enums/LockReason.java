package com.specialist.auth.domain.account.models.enums;

import com.specialist.auth.exceptions.UnknownLockReasonTypeException;
import lombok.Getter;

import java.util.Arrays;

public enum LockReason {
    TYPE_SPAM(1),
    SPECIALIST_SPAM(2),
    REVIEW_SPAM(3),
    ABUSE(4);

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
