package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.specialistdirectory.exceptions.UnknownSpecialistLanguageException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SpecialistLanguageQueryConverter implements Converter<String, SpecialistLanguage> {
    @Override
    public SpecialistLanguage convert(@NonNull String source) {
        SpecialistLanguage language = SpecialistLanguage.fromJson(source);
        if (language == null) {
            throw new UnknownSpecialistLanguageException();
        }
        return language;
    }
}
