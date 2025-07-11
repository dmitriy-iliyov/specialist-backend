package com.aidcompass.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ContactType {
    EMAIL, PHONE_NUMBER;


    @JsonCreator
    public static ContactType toContactType(String stringType) {
        return Arrays.stream(ContactType.values())
                .filter(type -> type.toString().equalsIgnoreCase(stringType))
                .findFirst()
                .orElseThrow();
    }
}
