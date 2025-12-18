package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DisableReasonQueryConverter implements Converter<String, DisableReason> {

    @Override
    public DisableReason convert(@Nullable String source) {
        return source == null ? null : DisableReason.fromJson(source);
    }
}
