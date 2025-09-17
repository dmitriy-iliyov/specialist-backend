package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import com.specialist.utils.pagination.PageRequest;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;

@com.specialist.specialistdirectory.domain.specialist.validation.SpecialistFilter
@Getter
public class SpecialistFilter extends BaseSpecialistFilter implements SpecialistProjectionFilter {

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

        public SpecialistFilter(String city,
                                String cityCode,
                                Long typeId,
                                SpecialistLanguage lang,
                                Integer minRating,
                                Integer maxRating,
                                Boolean asc,
                                Integer pageNumber,
                                Integer pageSize) {
                super(asc, pageNumber, pageSize);
                this.city = city;
                this.cityCode = cityCode;
                this.typeId = typeId;
                this.lang = lang;
                this.minRating = minRating;
                this.maxRating = maxRating;
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
}
