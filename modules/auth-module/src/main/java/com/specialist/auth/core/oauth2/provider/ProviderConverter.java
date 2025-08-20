package com.specialist.auth.core.oauth2.provider;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProviderConverter implements AttributeConverter<Provider, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Provider provider) {
        return provider.getCode();
    }

    @Override
    public Provider convertToEntityAttribute(Integer integer) {
        return Provider.fromCode(integer);
    }
}
