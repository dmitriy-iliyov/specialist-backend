package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpecialistLanguageQueryConverter implements Converter<String, SpecialistLanguage> {
    @Override
    public SpecialistLanguage convert(@Nullable String source) {
        return source == null ? null : SpecialistLanguage.fromJson(source);
    }
}
