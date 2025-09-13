package com.specialist.user.mappers;

import com.specialist.user.models.enums.SpecialistStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SpecialistStatusConverter implements AttributeConverter<SpecialistStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SpecialistStatus specialistStatus) {
        return specialistStatus.getCode();
    }

    @Override
    public SpecialistStatus convertToEntityAttribute(Integer integer) {
        return SpecialistStatus.fromCode(integer);
    }
}
