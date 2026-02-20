package com.specialist.auth.domain.account.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.auth.exceptions.UnknownDisableReasonTypeException;
import com.specialist.auth.exceptions.UnsupportedDisableReasonException;
import lombok.Getter;

import java.util.Arrays;

public enum DisableReason {
    EMAIL_CONFIRMATION_REQUIRED(1),
    PERMANENTLY_SPAM(2),
    PERMANENTLY_ABUSE(3),
    ATTACK_ATTEMPT_DETECTED(4),
    SOFT_DELETE(5);

    @Getter
    private final int code;

    DisableReason(int code) {
        this.code = code;
    }

    public static DisableReason fromCode(int code) {
        return Arrays.stream(DisableReason.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownDisableReasonTypeException::new);
    }

    @JsonCreator
    public static DisableReason fromJson(String json) {
        return Arrays.stream(DisableReason.values())
                .filter(reason -> reason.name().equalsIgnoreCase(json))
                .findFirst()
                .orElseThrow(UnsupportedDisableReasonException::new);
    }
}
