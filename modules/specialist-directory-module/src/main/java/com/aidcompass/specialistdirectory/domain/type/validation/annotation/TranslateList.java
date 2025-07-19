package com.aidcompass.specialistdirectory.domain.type.validation.annotation;

import com.aidcompass.specialistdirectory.domain.type.validation.TranslateCreateListValidation;
import com.aidcompass.specialistdirectory.domain.type.validation.TranslateUpdateListValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TranslateUpdateListValidation.class, TranslateCreateListValidation.class})
public @interface TranslateList {
    String message() default "Translate list is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
