package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ApproverTypeConverter implements AttributeConverter<ApproverType,Integer> {

    @Override
    public Integer convertToDatabaseColumn(ApproverType approverType) {
        return approverType == null ? null : approverType.getCode();
    }

    @Override
    public ApproverType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : ApproverType.fromCode(integer);
    }
}
