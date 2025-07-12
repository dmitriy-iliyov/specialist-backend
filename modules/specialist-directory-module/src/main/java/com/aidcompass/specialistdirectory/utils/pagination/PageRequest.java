package com.aidcompass.specialistdirectory.utils.pagination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public record PageRequest(
        @PositiveOrZero(message = "Page number should be positive or zero.")
        int pageNumber,
        @Min(value = 10, message = "Min page size of pageNumber is 10.")
        int pageSize,
        Boolean asc
) implements PageDataHolder {
        public String cacheKey() {
                return "a:" + asc + ":n:" + pageNumber + ":s:" + pageSize;
        }
}