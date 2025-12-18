package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.LockReason;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LockReasonQueryConverter implements Converter<String, LockReason> {

    @Override
    public LockReason convert(@Nullable String source) {
        return source == null ? null : LockReason.fromJson(source);
    }
}
