package com.specialist.user.mappers;

import com.specialist.contracts.user.UserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserTypeConverter implements AttributeConverter<UserType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserType type) {
        return type.getCode();
    }

    @Override
    public UserType convertToEntityAttribute(Integer integer) {
        return UserType.fromCode(integer);
    }
}
