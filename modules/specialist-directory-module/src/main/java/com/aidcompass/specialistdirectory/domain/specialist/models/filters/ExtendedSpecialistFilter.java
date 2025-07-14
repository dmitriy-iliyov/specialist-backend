package com.aidcompass.specialistdirectory.domain.specialist.models.filters;

import com.aidcompass.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageDataHolder;
import com.aidcompass.specialistdirectory.domain.specialist.validation.annotation.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@Rating
public record ExtendedSpecialistFilter(
        @NotBlank(message = "City is required.")
        String city,

        @Positive(message = "Type id should be positive.")
        Long typeId,

        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        Integer minRating,

        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        Integer maxRating,

        Boolean asc,

        @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters.")
        String firstName,

        @Size(min = 2, max = 20, message = "Second name must be between 2 and 20 characters.")
        String secondName,

        @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters.")
        String lastName,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize
) implements BaseSpecialistFilter, PageDataHolder {

        public String cacheKey() {
                return ":c:" + city + ":t:" + typeId + ":mir:" + minRating + ":mar:" + maxRating +
                       ":a:" + asc + ":fn:" + firstName + ":sn:" + secondName + ":ln:" + lastName +
                       ":pn:" + pageNumber + ":ps:" + pageSize;
        }
}
