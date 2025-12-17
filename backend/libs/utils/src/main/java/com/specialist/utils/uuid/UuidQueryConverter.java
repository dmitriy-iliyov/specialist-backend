package com.specialist.utils.uuid;

import org.springframework.core.convert.converter.Converter;

import java.util.UUID;
import java.util.regex.Pattern;

public class UuidQueryConverter implements Converter<String, UUID> {

    private final Pattern pattern = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-7[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );

    @Override
    public UUID convert(String source) {
        if (!pattern.matcher(source).matches()) {
            throw new IllegalArgumentException("Invalid UUID format.");
        }
        return UUID.fromString(source);
    }
}
