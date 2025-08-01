package com.aidcompass.auth.domain.account.mappers;

import com.aidcompass.auth.domain.account.models.enums.UnableReasonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UnableReasonTypeConverter implements AttributeConverter<UnableReasonType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UnableReasonType unableReasonType) {
        return unableReasonType.getCode();
    }

    @Override
    public UnableReasonType convertToEntityAttribute(Integer integer) {
        return UnableReasonType.fromCode(integer);
    }
}
