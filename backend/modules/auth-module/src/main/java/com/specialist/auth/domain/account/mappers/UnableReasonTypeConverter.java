package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UnableReasonTypeConverter implements AttributeConverter<DisableReason, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DisableReason disableReasonType) {
        return disableReasonType == null ? null : disableReasonType.getCode();
    }

    @Override
    public DisableReason convertToEntityAttribute(Integer integer) {
        return integer == null ? null : DisableReason.fromCode(integer);
    }
}
