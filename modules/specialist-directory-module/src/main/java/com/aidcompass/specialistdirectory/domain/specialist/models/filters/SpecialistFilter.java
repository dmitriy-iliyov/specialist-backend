package com.aidcompass.specialistdirectory.domain.specialist.models.filters;

import com.aidcompass.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageDataHolder;
import jakarta.validation.constraints.*;

@com.aidcompass.specialistdirectory.domain.specialist.validation.SpecialistFilter
public record SpecialistFilter(

        @Size(max = 30, message = "City title lengths should be less 30 characters.")
        String city,

        @Pattern(regexp = "\\d{5}", message = "City code should be exactly 5 digits.")
        String cityCode,

        @Positive(message = "Type id should be positive.")
        Long typeId,

        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        Integer minRating,

        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        Integer maxRating,

        Boolean asc,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize
) implements BaseSpecialistFilter, PageDataHolder {

        public String cacheKey() {
                return ":c:" + city + ":t:" + typeId + ":mir:" + minRating +
                       ":mar:" + maxRating + ":a:" + asc + ":pn:" + pageNumber + ":ps:" + pageSize;
        }
}