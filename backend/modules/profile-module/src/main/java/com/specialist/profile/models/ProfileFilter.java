package com.specialist.profile.models;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.utils.pagination.PageDataHolder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProfileFilter (

        ProfileType type,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        @Max(value = 50, message = "Min page size is 50.")
        Integer pageSize,

        Boolean asc
) implements PageDataHolder { }
