package com.specialist.profile.mappers;

import com.specialist.profile.models.enums.SpecialistStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SpecialistProfileStatusConverter implements AttributeConverter<SpecialistStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SpecialistStatus specialistStatus) {
        return specialistStatus.getCode();
    }

    @Override
    public SpecialistStatus convertToEntityAttribute(Integer integer) {
        return SpecialistStatus.fromCode(integer);
    }
}
