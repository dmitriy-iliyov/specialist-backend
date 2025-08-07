package com.specialist.auth.domain.refresh_token.models;

import com.specialist.auth.exceptions.UnknownRefreshTokenStatusException;
import lombok.Getter;

import java.util.Arrays;

public enum RefreshTokenStatus {
    ACTIVE(1),
    DEACTIVATED(2),
    REVOKED(3);

    @Getter
    private final int code;

    RefreshTokenStatus(int code) {
        this.code = code;
    }

    public static RefreshTokenStatus fromCode(int code) {
        return Arrays.stream(RefreshTokenStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownRefreshTokenStatusException::new);
    }
}
