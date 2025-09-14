package com.specialist.user.mappers;

import com.specialist.contracts.user.ProfileType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProfileTypeConverter implements AttributeConverter<ProfileType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProfileType profileType) {
        return profileType.getCode();
    }

    @Override
    public ProfileType convertToEntityAttribute(Integer integer) {
        return ProfileType.fromCode(integer);
    }
}
