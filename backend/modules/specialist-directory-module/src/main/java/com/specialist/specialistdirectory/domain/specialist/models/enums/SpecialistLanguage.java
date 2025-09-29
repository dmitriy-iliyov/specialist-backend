package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.specialistdirectory.exceptions.UnknownSpecialistLanguageException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SpecialistLanguage {
    UA(1),
    RU(2),
    DE(3),
    EN(4);

    private final int code;

    SpecialistLanguage(int code) {
        this.code = code;
    }

    public static SpecialistLanguage fromCode(int code) {
        return Arrays.stream(SpecialistLanguage.values())
                .filter(language -> language.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownSpecialistLanguageException::new);
    }

    @JsonCreator
    public static SpecialistLanguage fromJson(String language) {
        return Arrays.stream(SpecialistLanguage.values())
                .filter(l -> l.name().equalsIgnoreCase(language))
                .findFirst()
                .orElse(null);
    }
}
