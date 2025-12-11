package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.specialist.specialistdirectory.exceptions.UnsupportedGenderException;

import java.util.Arrays;

public enum Gender {
    MALE,
    FEMALE,
    ANOTHER,
    NOT_MENTIONED;

    @JsonValue
    public String toJson() {
        return this.name();
    }

    @JsonCreator
    public static Gender fromString(String value) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.name().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(UnsupportedGenderException::new);
    }
}
