package com.aidcompass.auth.domain.refresh_token;

import com.aidcompass.auth.domain.refresh_token.models.RefreshTokenStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RefreshTokenStatusConverter implements AttributeConverter<RefreshTokenStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RefreshTokenStatus status) {
        return status.getCode();
    }

    @Override
    public RefreshTokenStatus convertToEntityAttribute(Integer integer) {
        return RefreshTokenStatus.fromCode(integer);
    }
}
