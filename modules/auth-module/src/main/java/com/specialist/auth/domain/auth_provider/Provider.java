package com.specialist.auth.domain.auth_provider;

import com.specialist.auth.exceptions.UnsupportedProviderException;
import lombok.Getter;

import java.util.Arrays;

public enum Provider {
    LOCAL(1),
    GOOGLE(2),
    FACEBOOK(3),
    APPLE(4);

    @Getter
    private final int code;

    Provider(int code) {
        this.code = code;
    }

    public static Provider fromCode(int code) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.getCode() == code)
                .findFirst()
                .orElseThrow(UnsupportedProviderException::new);
    }

    public static Provider fromName(String name) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(UnsupportedProviderException::new);
    }
}
