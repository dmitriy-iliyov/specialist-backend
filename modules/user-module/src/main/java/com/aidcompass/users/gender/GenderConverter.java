package com.aidcompass.users.gender;

import com.aidcompass.users.general.exceptions.gender.GenderConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            throw new GenderConvertException();
        }
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(Integer code) {
        if (code == null) {
            throw new GenderConvertException();
        }
        return Gender.fromCode(code);
    }
}
