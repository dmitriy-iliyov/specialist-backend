package com.specialist.auth.domain.authority;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.auth.exceptions.UnsupportedAuthorityException;

import java.util.Arrays;

public enum Authority {
    ACCOUNT_CREATE, ACCOUNT_DELETE, ACCOUNT_LOCK, ACCOUNT_DISABLE, ACCOUNT_MANAGER, SERVICE_ACCOUNT_MANAGER,
    TYPE_SUGGEST, TYPE_MANAGEMENT, TYPE_TRANSLATE_MANAGEMENT,
    SPECIALIST_CREATE, SPECIALIST_UPDATE, SPECIALIST_APPROVE, SPECIALIST_MANAGEMENT,
    REVIEW_CREATE_UPDATE, REVIEW_MANAGEMENT, REVIEWS_BUFFER_MANAGEMENT,
    READ_METRICS;

    @JsonCreator
    public static Authority fromJson(String authority) {
        return Arrays.stream(Authority.values())
                .filter(a -> a.name().equalsIgnoreCase(authority))
                .findFirst()
                .orElseThrow(UnsupportedAuthorityException::new);
    }
}
