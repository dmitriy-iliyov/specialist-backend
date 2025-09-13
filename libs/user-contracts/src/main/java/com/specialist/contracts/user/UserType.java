package com.specialist.contracts.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserType {
    USER(1, "ROLE_USER"),
    SPECIALIST(2, "ROLE_SPECIALIST");

    private final int code;
    private final String stringRole;

    UserType(int code, String stringRole) {
        this.code = code;
        this.stringRole = stringRole;
    }

    public static UserType fromCode(int code) {
        return Arrays.stream(UserType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static UserType fromJson(String json) {
        return Arrays.stream(UserType.values())
                .filter(role -> role.name().equalsIgnoreCase(json))
                .findFirst()
                .orElse(null);
    }

    public static UserType fromStringRole(String stringRole) {
        return Arrays.stream(UserType.values())
                .filter(role -> role.getStringRole().equalsIgnoreCase(stringRole))
                .findFirst()
                .orElse(null);
    }
}