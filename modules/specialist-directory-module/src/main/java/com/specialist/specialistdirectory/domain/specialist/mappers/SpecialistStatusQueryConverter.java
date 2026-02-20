package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpecialistStatusQueryConverter implements Converter<String, SpecialistStatus> {
    @Override
    public SpecialistStatus convert(@Nullable String source) {
        return source == null ? null : SpecialistStatus.fromJson(source);
    }
}
