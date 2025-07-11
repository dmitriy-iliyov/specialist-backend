package com.aidcompass.specialistdirectory.utils;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public record PageRequest(
        @PositiveOrZero(message = "Page number should be positive or zero.")
        int number,
        @Min(value = 10, message = "Min page size of pageNumber is 10.")
        int size,
        Boolean asc
) {
        public String cacheKey() {
                return "a:" + asc + ":n:" + number + ":s:" + size;
        }
}