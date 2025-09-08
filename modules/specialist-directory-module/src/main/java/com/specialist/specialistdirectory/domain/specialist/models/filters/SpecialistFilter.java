package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import jakarta.validation.constraints.*;
import lombok.Data;

@com.specialist.specialistdirectory.domain.specialist.validation.SpecialistFilter
@Data
public class SpecialistFilter implements BaseSpecialistFilter, PageDataHolder {

        @Size(max = 30, message = "City title lengths should be less 30 characters.")
        protected final String city;

        @Pattern(regexp = "\\d{5}", message = "City code should be exactly 5 digits.")
        protected final String cityCode;

        @Positive(message = "Type id should be positive.")
        protected final Long typeId;

        protected final SpecialistLanguage lang;

        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        protected final Integer minRating;

        @Min(value = 0, message = "Min value is 0.")
        @Max(value = 5, message = "Max value is 5.")
        protected final Integer maxRating;

        protected final Boolean asc;

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        protected final Integer pageNumber;

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        @Max(value = 50, message = "Min page size is 50.")
        protected final Integer pageSize;

        public SpecialistFilter(String city,
                                String cityCode,
                                Long typeId,
                                SpecialistLanguage lang,
                                Integer minRating,
                                Integer maxRating,
                                Boolean asc,
                                Integer pageNumber,
                                Integer pageSize) {
                this.city = city;
                this.cityCode = cityCode;
                this.typeId = typeId;
                this.lang = lang;
                this.minRating = minRating;
                this.maxRating = maxRating;
                this.asc = asc;
                this.pageNumber = pageNumber;
                this.pageSize = pageSize;
        }

        public String cacheKey() {
                return ":c:" + city + ":t:" + typeId + ":mir:" + minRating +
                        ":mar:" + maxRating + ":a:" + asc + ":pn:" + pageNumber + ":ps:" + pageSize;
        }

        @Override
        public boolean isEmpty() {
                return city == null &&
                        cityCode == null &&
                        typeId == null &&
                        lang == null &&
                        minRating == null &&
                        maxRating == null;
        }

        public PageRequest toPageRequest() {
                return new PageRequest(this.pageNumber, this.pageSize, this.asc);
        }

        @Override
        public Boolean asc() {
                return asc;
        }

        @Override
        public Integer pageNumber() {
                return pageNumber;
        }

        @Override
        public Integer pageSize() {
                return pageSize;
        }
}
