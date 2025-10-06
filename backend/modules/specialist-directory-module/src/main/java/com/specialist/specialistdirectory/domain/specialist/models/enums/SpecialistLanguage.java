package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.specialistdirectory.exceptions.UnknownSpecialistLanguageException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SpecialistLanguage {
    UA, RU, DE, EN;

    @JsonCreator
    public static SpecialistLanguage fromJson(String language) {
        return Arrays.stream(SpecialistLanguage.values())
                .filter(l -> l.name().equals(language))
                .findFirst()
                .orElseThrow(UnknownSpecialistLanguageException::new);
    }
}
