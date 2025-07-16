package com.aidcompass.specialistdirectory.domain.review.models.filters;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ReviewSort(
        SortType sortBy,

        Boolean asc,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize
) { }
