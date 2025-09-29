package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class SpecialistLanguageConverter implements AttributeConverter<List<SpecialistLanguage>, String> {

    @Override
    public String convertToDatabaseColumn(List<SpecialistLanguage> specialistLanguages) {
        if (specialistLanguages == null || specialistLanguages.isEmpty()) {
            return "";
        }
        return specialistLanguages.stream()
                .map(language -> String.valueOf(language.getCode()))
                .collect(Collectors.joining(","));
    }

    @Override
    public List<SpecialistLanguage> convertToEntityAttribute(String rawLanguages) {
        List<SpecialistLanguage> languages = new ArrayList<>();
        if (rawLanguages == null || rawLanguages.isBlank()) {
            return languages;
        }
        for (String langCode: rawLanguages.split(",")) {
            languages.add(SpecialistLanguage.fromCode(Integer.parseInt(langCode)));
        }
        return languages;
    }
}
