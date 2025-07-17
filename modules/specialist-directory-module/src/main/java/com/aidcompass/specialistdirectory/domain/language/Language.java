package com.aidcompass.specialistdirectory.domain.language;

import com.aidcompass.specialistdirectory.exceptions.UnsupportedLanguageException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Language {
    UA(0),
    DE(1),
    EN(2);

    private final int code;

    Language(int code) {
        this.code = code;
    }

    public static Language fromCode(int code) {
        return Arrays.stream(Language.values())
                .filter(language -> language.getCode() == code)
                .findFirst()
                .orElseThrow(UnsupportedLanguageException::new);
    }
}
