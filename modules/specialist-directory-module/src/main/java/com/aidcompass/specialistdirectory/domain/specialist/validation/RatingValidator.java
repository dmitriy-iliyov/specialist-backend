package com.aidcompass.specialistdirectory.domain.specialist.validation;

import com.aidcompass.specialistdirectory.domain.specialist.models.markers.RatingHolder;
import com.aidcompass.specialistdirectory.domain.specialist.validation.annotation.Rating;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<Rating, RatingHolder> {

    @Override
    public boolean isValid(RatingHolder holder, ConstraintValidatorContext constraintValidatorContext) {
        if (holder.minRating() != null && holder.maxRating() != null) {
            return holder.minRating() < holder.maxRating();
        }
        return true;
    }
}
