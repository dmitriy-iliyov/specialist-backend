package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DeliveryStateConverter implements AttributeConverter<DeliveryState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DeliveryState state) {
        return state.getCode();
    }

    @Override
    public DeliveryState convertToEntityAttribute(Integer code) {
        return DeliveryState.fromCode(code);
    }
}
