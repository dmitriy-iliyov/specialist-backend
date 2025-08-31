package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.specialist.specialistdirectory.exceptions.UnsupportedContactTypeException;

import java.util.Arrays;

public enum ContactType {
    EMAIL("email"),
    PHONE_NUMBER("phone");

    private final String jsonValue;

    ContactType(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonCreator
    public static ContactType fromJson(String json) {
        return Arrays.stream(ContactType.values())
                .filter(contact -> contact.toString().equalsIgnoreCase(json))
                .findFirst()
                .orElseThrow(UnsupportedContactTypeException::new);
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }
}