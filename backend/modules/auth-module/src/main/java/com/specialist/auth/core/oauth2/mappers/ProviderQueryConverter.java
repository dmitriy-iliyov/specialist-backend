package com.specialist.auth.core.oauth2.mappers;


import com.specialist.auth.core.oauth2.models.Provider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProviderQueryConverter implements Converter<String, Provider> {

    @Override
    public Provider convert(@NonNull String source) {
        return Provider.fromJson(source);
    }
}
