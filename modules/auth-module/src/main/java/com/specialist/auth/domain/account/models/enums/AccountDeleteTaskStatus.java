package com.specialist.auth.domain.account.models.enums;

import com.specialist.auth.exceptions.UnexpectedAccountDeleteTaskStatusCodeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AccountDeleteTaskStatus {
    READY_TO_SEND(1),
    SENDING(2),
    SENT(3),
    FAILED(4);

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
