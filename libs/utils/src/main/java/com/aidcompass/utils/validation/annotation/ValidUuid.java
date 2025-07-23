package com.aidcompass.utils.validation.annotation;

import com.aidcompass.utils.validation.UuidValidator;
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
    String paramName() default "uuid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
