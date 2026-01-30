package com.specialist.specialistdirectory.ut.domain.type.validation;

import com.specialist.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
import com.specialist.specialistdirectory.domain.type.validation.UniqueTypeCreateValidator;
import jakarta.validation.ConstraintValidatorContext;
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
class UniqueTypeCreateValidatorUnitTests {

    @Mock
    private TypeService service;

    @InjectMocks
    private UniqueTypeCreateValidator validator;

    @Test
    @DisplayName("UT: isValid() when type exists should return false")
    void isValid_exists_shouldReturnFalse() {
        TypeCreateDto dto = new TypeCreateDto("title");
        when(service.existsByTitleIgnoreCase("title")).thenReturn(true);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type not exists should return true")
    void isValid_notExists_shouldReturnTrue() {
        TypeCreateDto dto = new TypeCreateDto("title");
        when(service.existsByTitleIgnoreCase("title")).thenReturn(false);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(dto, context));
    }
}
