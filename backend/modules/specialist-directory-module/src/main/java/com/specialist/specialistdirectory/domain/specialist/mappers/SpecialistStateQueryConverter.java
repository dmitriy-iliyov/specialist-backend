package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpecialistStateQueryConverter implements Converter<String, SpecialistState> {
    @Override
    public SpecialistState convert(@Nullable String source) {
        return source == null ? null : SpecialistState.fromJson(source);
    }
}
