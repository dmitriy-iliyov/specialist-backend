package com.specialist.specialistdirectory.ut.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.markers.SpecialistMarker;
import com.specialist.specialistdirectory.domain.specialist.validation.SpecialistValidator;
import com.specialist.specialistdirectory.domain.type.services.TypeConstants;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialistValidatorUnitTests {

    @Mock
    private TypeService typeService;

    @InjectMocks
    private SpecialistValidator validator;

    private static final Long OTHER_TYPE_ID = 999L;

    @BeforeAll
    static void setUp() {
        TypeConstants.OTHER_TYPE_ID = OTHER_TYPE_ID;
    }

    @Test
    @DisplayName("UT: isValid() when type not exists should return false")
    void isValid_typeNotExists_shouldReturnFalse() {
        SpecialistMarker dto = mock(SpecialistMarker.class);
        when(dto.getTypeId()).thenReturn(1L);
        when(typeService.existsById(1L)).thenReturn(false);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type exists and not OTHER should return true")
    void isValid_typeExistsNotOther_shouldReturnTrue() {
        SpecialistMarker dto = mock(SpecialistMarker.class);
        when(dto.getTypeId()).thenReturn(1L);
        when(typeService.existsById(1L)).thenReturn(true);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type OTHER and anotherType null should return false")
    void isValid_typeOtherNullAnotherType_shouldReturnFalse() {
        SpecialistMarker dto = mock(SpecialistMarker.class);
        when(dto.getTypeId()).thenReturn(OTHER_TYPE_ID);
        when(dto.getAnotherType()).thenReturn(null);
        when(typeService.existsById(OTHER_TYPE_ID)).thenReturn(true);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type OTHER and anotherType blank should return false")
    void isValid_typeOtherBlankAnotherType_shouldReturnFalse() {
        SpecialistMarker dto = mock(SpecialistMarker.class);
        when(dto.getTypeId()).thenReturn(OTHER_TYPE_ID);
        when(dto.getAnotherType()).thenReturn("   ");
        when(typeService.existsById(OTHER_TYPE_ID)).thenReturn(true);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type OTHER and anotherType valid should return true")
    void isValid_typeOtherValidAnotherType_shouldReturnTrue() {
        SpecialistMarker dto = mock(SpecialistMarker.class);
        when(dto.getTypeId()).thenReturn(OTHER_TYPE_ID);
        when(dto.getAnotherType()).thenReturn("Valid Type");
        when(typeService.existsById(OTHER_TYPE_ID)).thenReturn(true);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(dto, context));
    }
}
