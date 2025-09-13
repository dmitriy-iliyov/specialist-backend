package com.specialist.user.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.user.exceptions.UnknownUserTypeCodeException;
import com.specialist.user.exceptions.UnsupportedUserTypeException;
import lombok.Getter;

import java.util.Arrays;

public enum UserType {
    USER(1),
    SPECIALIST(2);

    @Getter
    private final int code;

    UserType(int code) {
        this.code = code;
    }

    public static UserType fromCode(int code) {
        return Arrays.stream(UserType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownUserTypeCodeException::new);
    }

    @JsonCreator
    public static UserType fromJson(String json) {
        return Arrays.stream(UserType.values())
                .filter(role -> role.name().equalsIgnoreCase(json))
                .findFirst()
                .orElseThrow(UnsupportedUserTypeException::new);
    }
}