package com.aidcompass.specialistdirectory.domain.specialist.models.filters;

import com.aidcompass.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.markers.PageableFilter;
import com.aidcompass.specialistdirectory.domain.specialist.validation.annotation.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@Rating
public record ExtendedSpecialistFilter(
        @NotBlank(message = "City is required.")
        String city,

        @JsonProperty("type_id")
        @Positive(message = "Type id should be positive.")
        Long typeId,

        @JsonProperty("min_rating")
        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        Integer minRating,

        @JsonProperty("max_rating")
        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        Integer maxRating,

        Boolean asc,

        @JsonProperty("first_name")
        @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters.")
        String firstName,

        @JsonProperty("second_name")
        @Size(min = 2, max = 20, message = "Second name must be between 2 and 20 characters.")
        String secondName,

        @JsonProperty("last_name")
        @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters.")
        String lastName,

        @JsonProperty("page_number")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        int pageNumber,

        @JsonProperty("page_size")
        @Min(value = 10, message = "Min page size is 10.")
        int pageSize) implements BaseSpecialistFilter, PageableFilter {

        public String cacheKey() {
                return ":c:" + city + ":t:" + typeId + ":mir:" + minRating + ":mar:" + maxRating +
                       ":a:" + asc + ":fn:" + firstName + ":sn:" + secondName + ":ln:" + lastName +
                       ":pn:" + pageNumber + ":ps:" + pageSize;
        }
}
