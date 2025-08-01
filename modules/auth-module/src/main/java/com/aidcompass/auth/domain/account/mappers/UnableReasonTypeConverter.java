package com.aidcompass.auth.domain.account.mappers;

import com.aidcompass.auth.domain.account.models.enums.UnableReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UnableReasonTypeConverter implements AttributeConverter<UnableReason, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UnableReason unableReasonType) {
        return unableReasonType.getCode();
    }

    @Override
    public UnableReason convertToEntityAttribute(Integer integer) {
        return UnableReason.fromCode(integer);
    }
}
