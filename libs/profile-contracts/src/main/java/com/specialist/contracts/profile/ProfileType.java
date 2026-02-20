package com.specialist.contracts.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProfileType {
    USER(1, "ROLE_USER"),
    SPECIALIST(2, "ROLE_SPECIALIST");

    private final int code;
    private final String stringRole;

    ProfileType(int code, String stringRole) {
        this.code = code;
        this.stringRole = stringRole;
    }

    public static ProfileType fromCode(int code) {
        return Arrays.stream(ProfileType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static ProfileType fromJson(String json) {
        return Arrays.stream(ProfileType.values())
                .filter(role -> role.name().equalsIgnoreCase(json))
                .findFirst()
                .orElse(null);
    }

    public static ProfileType fromStringRole(String stringRole) {
        return Arrays.stream(ProfileType.values())
                .filter(role -> role.getStringRole().equalsIgnoreCase(stringRole))
                .findFirst()
                .orElse(null);
    }
}