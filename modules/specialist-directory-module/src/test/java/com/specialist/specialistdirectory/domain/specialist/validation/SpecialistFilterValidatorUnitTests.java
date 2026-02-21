package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistProjectionFilter;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpecialistFilterValidatorUnitTests {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder;

    private final SpecialistFilterValidator validator = new SpecialistFilterValidator();

    @BeforeEach
    void setUp() {
        lenient().when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        lenient().when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
    }

    @Test
    @DisplayName("UT: isValid() when empty should return true")
    void isValid_empty_shouldReturnTrue() {
        SpecialistProjectionFilter filter = mock(SpecialistProjectionFilter.class);
        when(filter.isEmpty()).thenReturn(true);
        assertTrue(validator.isValid(filter, context));
    }

    @Test
    @DisplayName("UT: isValid() when minRating > maxRating should return false")
    void isValid_invalidRating_shouldReturnFalse() {
        SpecialistProjectionFilter filter = mock(SpecialistProjectionFilter.class);
        when(filter.isEmpty()).thenReturn(false);
        when(filter.getMinRating()).thenReturn(5);
        when(filter.getMaxRating()).thenReturn(4);
        when(filter.getCity()).thenReturn("city");

        assertFalse(validator.isValid(filter, context));
    }

    @Test
    @DisplayName("UT: isValid() when no city or code should return false")
    void isValid_noCityOrCode_shouldReturnFalse() {
        SpecialistProjectionFilter filter = mock(SpecialistProjectionFilter.class);
        when(filter.isEmpty()).thenReturn(false);
        when(filter.getCity()).thenReturn(null);
        when(filter.getCityCode()).thenReturn(null);

        assertFalse(validator.isValid(filter, context));
    }

    @Test
    @DisplayName("UT: isValid() when valid should return true")
    void isValid_valid_shouldReturnTrue() {
        SpecialistProjectionFilter filter = mock(SpecialistProjectionFilter.class);
        when(filter.isEmpty()).thenReturn(false);
        when(filter.getMinRating()).thenReturn(3);
        when(filter.getMaxRating()).thenReturn(5);
        when(filter.getCity()).thenReturn("city");

        assertTrue(validator.isValid(filter, context));
    }
}
