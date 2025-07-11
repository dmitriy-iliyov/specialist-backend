package com.aidcompass.specialistdirectory.domain.specialist.validation.annotation;

import com.aidcompass.specialistdirectory.domain.specialist.validation.RatingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface Rating {
    String message() default "Invalid filter.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
