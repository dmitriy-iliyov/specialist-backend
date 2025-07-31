package com.aidcompass.auth.domain.account.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailUniquenessValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "Email should be unique.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
