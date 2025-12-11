package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReviewStatusQueryConverter implements Converter<String, ReviewStatus> {

    @Override
    public ReviewStatus convert(String source) {
        return ReviewStatus.fromString(source);
    }
}
