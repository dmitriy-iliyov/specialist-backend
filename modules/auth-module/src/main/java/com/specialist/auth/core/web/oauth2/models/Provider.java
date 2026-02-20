package com.specialist.auth.core.web.oauth2.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.auth.exceptions.UnsupportedProviderException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Provider {
    LOCAL(1, "local"),
    GOOGLE(2, "google"),
    FACEBOOK(3, "facebook"),
    APPLE(4, "apple");

    private final int code;
    private final String registrationId;

    Provider(int code, String registrationId) {
        this.code = code;
        this.registrationId = registrationId;
    }

    public static Provider fromCode(int code) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.getCode() == code)
                .findFirst()
                .orElseThrow(UnsupportedProviderException::new);
    }

    @JsonCreator
    public static Provider fromJson(String json) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.getRegistrationId().equalsIgnoreCase(json))
                .findFirst()
                .orElseThrow(UnsupportedProviderException::new);
    }
}
