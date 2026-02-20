package com.specialist.specialistdirectory.domain.review.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ReviewPayload(
        @NotBlank(message = "Description is required.")
        String description,

        @NotNull(message = "Rating is required.")
        @PositiveOrZero(message = "Rating should be positive.")
        @Max(value = 5, message = "Max rating value is 5.")
        Integer rating
) { }
