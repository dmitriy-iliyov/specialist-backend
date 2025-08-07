package com.specialist.specialistdirectory.domain.contact;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ContactTypeConverter implements AttributeConverter<ContactType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(ContactType type) {
        return type.getCode();
    }

    @Override
    public ContactType convertToEntityAttribute(Integer code) {
        return ContactType.fromCode(code);
    }
}
