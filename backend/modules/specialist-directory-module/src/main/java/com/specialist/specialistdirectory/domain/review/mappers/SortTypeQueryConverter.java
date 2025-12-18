package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.specialistdirectory.domain.review.models.enums.SortType;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortTypeQueryConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(@Nullable String source) {
        return source == null ? null : SortType.fromJson(source);
    }
}
