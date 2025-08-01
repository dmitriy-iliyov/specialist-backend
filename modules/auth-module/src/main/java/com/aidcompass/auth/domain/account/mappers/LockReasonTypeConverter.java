package com.aidcompass.auth.domain.account.mappers;

import com.aidcompass.auth.domain.account.models.enums.LockReasonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LockReasonTypeConverter implements AttributeConverter<LockReasonType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LockReasonType lockReasonType) {
        return lockReasonType.getCode();
    }

    @Override
    public LockReasonType convertToEntityAttribute(Integer integer) {
        return LockReasonType.fromCode(integer);
    }
}
