package com.specialist.auth.domain.account.models.enums;

import com.specialist.auth.exceptions.UnexpectedAccountDeleteTaskStatusCodeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AccountDeleteTaskStatus {
    READY_TO_SEND(1),
    SENT(2),
    FAILED(3);

    private final int code;

    AccountDeleteTaskStatus(int code) {
        this.code = code;
    }

    public static AccountDeleteTaskStatus fromCode(int code) {
        return Arrays.stream(AccountDeleteTaskStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(UnexpectedAccountDeleteTaskStatusCodeException::new);
    }
}
