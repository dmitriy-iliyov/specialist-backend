package com.aidcompass.auth.domain.account.validation;

import com.aidcompass.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.aidcompass.contracts.auth.SystemAccountService;
import com.aidcompass.core.exceptions.models.BaseNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, DefaultAccountCreateDto> {

    private final SystemAccountService accountService;

    @Override
    public boolean isValid(DefaultAccountCreateDto dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (dto == null) {
            context.buildConstraintViolationWithTemplate("Data is required.")
                    .addPropertyNode("account")
                    .addConstraintViolation();
            return false;
        }

        try {
            accountService.existsByEmail(dto.getEmail());
            context.buildConstraintViolationWithTemplate("Email isn't unique.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }
}
