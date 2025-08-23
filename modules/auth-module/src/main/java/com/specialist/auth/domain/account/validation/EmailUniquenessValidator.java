package com.specialist.auth.domain.account.validation;

import com.specialist.auth.domain.account.services.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AccountService accountService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (email == null) {
            context.buildConstraintViolationWithTemplate("Email is required.")
                    //.addPropertyNode("account")
                    .addConstraintViolation();
            return false;
        }

        if (accountService.existsByEmail(email)) {
            context.buildConstraintViolationWithTemplate("Email isn't unique.")
                    //.addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
