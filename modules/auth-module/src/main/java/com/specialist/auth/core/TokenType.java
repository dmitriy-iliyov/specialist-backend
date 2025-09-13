package com.specialist.auth.core;

import com.specialist.auth.core.web.CookieType;
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
