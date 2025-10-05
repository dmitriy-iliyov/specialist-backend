package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AccountDeleteTaskStatusConverter implements AttributeConverter<AccountDeleteTaskStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountDeleteTaskStatus status) {
        return status.getCode();
    }

    @Override
    public AccountDeleteTaskStatus convertToEntityAttribute(Integer integer) {
        return AccountDeleteTaskStatus.fromCode(integer);
    }
}
