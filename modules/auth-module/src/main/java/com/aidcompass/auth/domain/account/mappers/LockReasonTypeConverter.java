package com.aidcompass.auth.domain.account.mappers;

import com.aidcompass.auth.domain.account.models.enums.LockReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LockReasonTypeConverter implements AttributeConverter<LockReason, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LockReason lockReason) {
        return lockReason.getCode();
    }

    @Override
    public LockReason convertToEntityAttribute(Integer integer) {
        return LockReason.fromCode(integer);
    }
}
