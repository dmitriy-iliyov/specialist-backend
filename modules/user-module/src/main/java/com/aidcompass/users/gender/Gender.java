package com.aidcompass.users.gender;


import com.aidcompass.users.general.exceptions.gender.GenderNotFoundByCode;
import com.aidcompass.users.general.exceptions.gender.UnsupportedGenderException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Gender {
    MALE("Чоловік", 0),
    FEMALE("Жінка", 1);

    private final String translate;
    @Getter
    private final int code;


    Gender(String translate, int code) {
        this.translate = translate;
        this.code = code;
    }

    @JsonValue
    public String getTranslate() {
        return translate;
    }

    @Override
    public String toString() {
        return translate;
    }

    public static Gender toEnum(String translate) {
        List<Gender> values = new ArrayList<>(List.of(Gender.values()));
        return values.stream()
                .filter(v -> v.getTranslate().equals(translate))
                .findFirst()
                .orElseThrow(UnsupportedGenderException::new);
    }

    public static Gender fromCode(int code) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getCode() == code)
                .findFirst()
                .orElseThrow(GenderNotFoundByCode::new);
    }
}
