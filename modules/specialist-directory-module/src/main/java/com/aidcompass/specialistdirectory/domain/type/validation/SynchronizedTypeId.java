package com.aidcompass.specialistdirectory.domain.type.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SynchronizedTypeIdValidator.class)
public @interface SynchronizedTypeId {
    String message() default "Type id isn't synchronized.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
