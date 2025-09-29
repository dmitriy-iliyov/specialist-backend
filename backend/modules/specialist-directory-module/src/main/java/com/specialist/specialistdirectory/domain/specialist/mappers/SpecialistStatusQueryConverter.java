package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.exceptions.UnsupportedSpecialistStatusCodeException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpecialistStatusQueryConverter implements Converter<String, SpecialistStatus> {
    @Override
    public SpecialistStatus convert(String source) {
        SpecialistStatus status = SpecialistStatus.fromJson(source);
        if (status == null) {
            throw new UnsupportedSpecialistStatusCodeException();
        }
        return status;
    }
}
