package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.LockReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LockReasonTypeConverter implements AttributeConverter<LockReason, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LockReason lockReason) {
        return lockReason == null ? null : lockReason.getCode();
    }

    @Override
    public LockReason convertToEntityAttribute(Integer integer) {
        return integer == null ? null : LockReason.fromCode(integer);
    }
}
