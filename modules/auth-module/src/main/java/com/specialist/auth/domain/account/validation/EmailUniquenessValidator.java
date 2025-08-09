package com.specialist.auth.domain.account.validation;

import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.services.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, DefaultAccountCreateDto> {

    private final AccountService accountService;

    @Override
    public boolean isValid(DefaultAccountCreateDto dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (dto == null) {
            context.buildConstraintViolationWithTemplate("Data is required.")
                    .addPropertyNode("account")
                    .addConstraintViolation();
            return false;
        }

        if (accountService.existsByEmail(dto.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email isn't unique.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
