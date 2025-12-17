package com.specialist.utils.uuid.annotation;

import com.specialist.utils.uuid.UUIDv7Validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UUIDv7Validator.class)
@Documented
public @interface UUIDv7 {
    String message() default "Invalid UUID format";
    String paramName() default "uuid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
