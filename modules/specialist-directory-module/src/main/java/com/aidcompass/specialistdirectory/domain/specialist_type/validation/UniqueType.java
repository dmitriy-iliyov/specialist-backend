package com.aidcompass.specialistdirectory.domain.specialist_type.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueTypeCreateValidator.class, UniqueTypeUpdateValidator.class})
public @interface UniqueType {
    String message() default "Type already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
