package com.specialist.auth.core.models;

import lombok.Getter;

public enum TokenType {
    REFRESH(CookieType.REFRESH_TOKEN),
    ACCESS(CookieType.ACCESS_TOKEN);

    @Getter
    private final CookieType cookieType;

    TokenType(CookieType cookieType) {
        this.cookieType = cookieType;
    }
}
