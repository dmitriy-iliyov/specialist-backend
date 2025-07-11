package com.aidcompass.specialistdirectory.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UuidValidator.class)
public @interface ValidUuid {
    String message() default "Invalid UUID format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
