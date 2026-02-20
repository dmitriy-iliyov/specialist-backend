package com.specialist.profile.mappers;

import com.specialist.contracts.profile.ProfileType;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileTypeQueryConverter implements Converter<String, ProfileType> {

    @Override
    public ProfileType convert(@Nullable String source) {
        return ProfileType.fromJson(source);
    }
}
