package com.aidcompass.specialistdirectory.domain.specialist.validation.annotation;

import com.aidcompass.specialistdirectory.domain.specialist.validation.SpecialistFilterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpecialistFilterValidator.class)
public @interface SpecialistFilter {
    String message() default "Filter should contains city or city code.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
