package com.specialist.auth.domain.account.validation;

import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.services.AccountService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailUniquenessValidatorUnitTests {

    @Mock
    AccountService accountService;

    @Mock
    ConstraintValidatorContext context;

    @Mock
    ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @InjectMocks
    EmailUniquenessValidator validator;

    @Test
    @DisplayName("UT: isValid() when dto valid and email unique should return true")
    public void isValid_whenDtoValid_shouldReturnTrue() {
        String email = "email@gmail.com";
        doNothing().when(context).disableDefaultConstraintViolation();
        when(accountService.existsByEmail(eq(email))).thenReturn(false);

        boolean result = validator.isValid(email, context);

        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(accountService, times(1)).existsByEmail(anyString());
        verifyNoMoreInteractions(context);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(constraintViolationBuilder);
        verifyNoInteractions(nodeBuilderCustomizableContext);
        assertTrue(result);
    }

    @Test
    @DisplayName("UT: isValid() when dto null should return false")
    public void isValid_whenDtoNull_shouldReturnFalse() {
        String email = null;

        doNothing().when(context).disableDefaultConstraintViolation();

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode("account")).thenReturn(nodeBuilderCustomizableContext);
        when(nodeBuilderCustomizableContext.addConstraintViolation()).thenReturn(context);

        boolean result = validator.isValid(email, context);

        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder, times(1)).addPropertyNode(anyString());
        verify(nodeBuilderCustomizableContext, times(1)).addConstraintViolation();
        verifyNoInteractions(accountService);
        verifyNoMoreInteractions(context);
        assertFalse(result);
    }

    @Test
    @DisplayName("UT: isValid() when email isn't unique should return false")
    public void isValid_whenEmailExists_shouldReturnFalse() {
        String email = "email@gmail.com";

        doNothing().when(context).disableDefaultConstraintViolation();
        when(accountService.existsByEmail(eq(email))).thenReturn(true);

        when(context.buildConstraintViolationWithTemplate("Email isn't unique.")).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode("email")).thenReturn(nodeBuilderCustomizableContext);
        when(nodeBuilderCustomizableContext.addConstraintViolation()).thenReturn(context);

        boolean result = validator.isValid(email, context);

        verify(context, times(1)).disableDefaultConstraintViolation();
        verify(context, times(1)).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder, times(1)).addPropertyNode(anyString());
        verify(nodeBuilderCustomizableContext, times(1)).addConstraintViolation();
        verify(accountService, times(1)).existsByEmail(anyString());
        verifyNoMoreInteractions(accountService);
        verifyNoMoreInteractions(context);
        assertFalse(result);
    }

}
