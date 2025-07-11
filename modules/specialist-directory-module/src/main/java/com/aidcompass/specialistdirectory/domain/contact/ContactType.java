package com.aidcompass.specialistdirectory.domain.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ContactType {
    EMAIL(0),
    PHONE_NUMBER(1);

    private final int code;

    ContactType(int code) {
        this.code = code;
    }

    public static ContactType fromCode(int code) {
        return Arrays.stream(ContactType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static ContactType toEnum(String json) {
        return Arrays.stream(ContactType.values())
                .filter(contact -> contact.toString().equals(json))
                .findFirst()
                .orElse(null);
    }
}