package com.specialist.specialistdirectory.domain.type.validation;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
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
class UniqueTypeUpdateValidatorUnitTests {

    @Mock
    private TypeService service;

    @InjectMocks
    private UniqueTypeUpdateValidator validator;

    @Test
    @DisplayName("UT: isValid() when type exists with different ID should return false")
    void isValid_existsDifferentId_shouldReturnFalse() {
        TypeUpdateDto dto = new TypeUpdateDto("title", true);
        dto.setId(1L);
        ShortTypeResponseDto existing = new ShortTypeResponseDto(2L, "title");

        when(service.findByTitle("title")).thenReturn(existing);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type exists with same ID should return true")
    void isValid_existsSameId_shouldReturnTrue() {
        TypeUpdateDto dto = new TypeUpdateDto("title", true);
        dto.setId(1L);
        ShortTypeResponseDto existing = new ShortTypeResponseDto(1L, "title");

        when(service.findByTitle("title")).thenReturn(existing);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when type not exists should return true")
    void isValid_notExists_shouldReturnTrue() {
        TypeUpdateDto dto = new TypeUpdateDto("title", true);
        dto.setId(1L);

        when(service.findByTitle("title")).thenThrow(new BaseNotFoundException() {
            @Override
            public ErrorDto getErrorDto() {
                return new ErrorDto("code", "message");
            }
        });

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(dto, context));
    }
}
