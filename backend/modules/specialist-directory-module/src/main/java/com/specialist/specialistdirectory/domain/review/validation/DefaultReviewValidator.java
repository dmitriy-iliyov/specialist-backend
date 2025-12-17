package com.specialist.specialistdirectory.domain.review.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewPayload;
import com.specialist.specialistdirectory.exceptions.ReviewSerializeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DefaultReviewValidator implements ReviewValidator {

    private final ObjectMapper mapper;
    private final Validator validator;

    @Override
    public ReviewPayload validate(String rawPayload) {
        ReviewPayload payload;
        try {
            payload = mapper.readValue(rawPayload, ReviewPayload.class);
        } catch (JsonProcessingException e) {
            throw new ReviewSerializeException(e.getMessage());
        }
        Set<ConstraintViolation<ReviewPayload>> errors = validator.validate(payload);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        return payload;
    }
}
