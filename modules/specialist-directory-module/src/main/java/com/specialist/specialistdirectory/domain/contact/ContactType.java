package com.specialist.specialistdirectory.domain.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ContactType {
    EMAIL("email"),
    PHONE_NUMBER("phone");

    private final String jsonValue;

    ContactType(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonCreator
    public static ContactType toEnum(String json) {
        return Arrays.stream(ContactType.values())
                .filter(contact -> contact.toString().equals(json))
                .findFirst()
                .orElse(null);
    }
}