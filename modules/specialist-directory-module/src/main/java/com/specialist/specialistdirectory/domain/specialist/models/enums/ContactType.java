package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ContactType {
    EMAIL,
    PHONE_NUMBER;

    @JsonCreator
    public static ContactType fromJson(String json) {
        return Arrays.stream(ContactType.values())
                .filter(contact -> contact.toString().equalsIgnoreCase(json))
                .findFirst()
                .orElse(null);
    }
}