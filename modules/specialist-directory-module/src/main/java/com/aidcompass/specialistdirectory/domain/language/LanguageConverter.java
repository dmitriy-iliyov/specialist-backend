package com.aidcompass.specialistdirectory.domain.language;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LanguageConverter implements AttributeConverter<Language, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Language language) {
        return language.getCode();
    }

    @Override
    public Language convertToEntityAttribute(Integer code) {
        return Language.fromCode(code);
    }
}
