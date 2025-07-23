package com.aidcompass.utils.pagination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PageRequest(
        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize,

        Boolean asc
) implements PageDataHolder {
        public String cacheKey() {
                return "a:" + asc + ":n:" + pageNumber + ":s:" + pageSize;
        }
}