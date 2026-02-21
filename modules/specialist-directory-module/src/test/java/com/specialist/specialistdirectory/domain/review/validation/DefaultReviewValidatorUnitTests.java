package com.specialist.specialistdirectory.domain.review.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewPayload;
import com.specialist.specialistdirectory.exceptions.ReviewSerializeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultReviewValidatorUnitTests {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private DefaultReviewValidator reviewValidator;

    @Test
    @DisplayName("UT: validate() when valid json and payload should return payload")
    void validate_valid_shouldReturnPayload() throws JsonProcessingException {
        String rawPayload = "{}";
        ReviewPayload payload = new ReviewPayload("text", 5);

        when(mapper.readValue(rawPayload, ReviewPayload.class)).thenReturn(payload);
        when(validator.validate(payload)).thenReturn(Collections.emptySet());

        ReviewPayload result = reviewValidator.validate(rawPayload);

        assertEquals(payload, result);
    }

    @Test
    @DisplayName("UT: validate() when invalid json should throw ReviewSerializeException")
    void validate_invalidJson_shouldThrowException() throws JsonProcessingException {
        String rawPayload = "invalid";
        when(mapper.readValue(rawPayload, ReviewPayload.class)).thenThrow(mock(JsonProcessingException.class));

        assertThrows(ReviewSerializeException.class, () -> reviewValidator.validate(rawPayload));
    }

    @Test
    @DisplayName("UT: validate() when validation fails should throw ConstraintViolationException")
    void validate_validationFails_shouldThrowException() throws JsonProcessingException {
        String rawPayload = "{}";
        ReviewPayload payload = new ReviewPayload("text", 5);
        ConstraintViolation<ReviewPayload> violation = mock(ConstraintViolation.class);

        when(mapper.readValue(rawPayload, ReviewPayload.class)).thenReturn(payload);
        when(validator.validate(payload)).thenReturn(Set.of(violation));

        assertThrows(ConstraintViolationException.class, () -> reviewValidator.validate(rawPayload));
    }
}
