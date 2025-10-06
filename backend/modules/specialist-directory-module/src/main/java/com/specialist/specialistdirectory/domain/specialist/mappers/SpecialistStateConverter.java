package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SpecialistStateConverter implements AttributeConverter<SpecialistState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SpecialistState specialistState) {
        return specialistState.getCode();
    }

    @Override
    public SpecialistState convertToEntityAttribute(Integer integer) {
        return SpecialistState.fromCode(integer);
    }
}
