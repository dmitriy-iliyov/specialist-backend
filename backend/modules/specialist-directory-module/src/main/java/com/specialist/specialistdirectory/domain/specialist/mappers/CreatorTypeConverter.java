package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreatorTypeConverter implements AttributeConverter<CreatorType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CreatorType creatorType) {
        return creatorType.getCode();
    }

    @Override
    public CreatorType convertToEntityAttribute(Integer integer) {
        return CreatorType.fromCode(integer);
    }
}
