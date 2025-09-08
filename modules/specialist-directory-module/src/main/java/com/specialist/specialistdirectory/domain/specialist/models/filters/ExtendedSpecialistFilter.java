package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@com.specialist.specialistdirectory.domain.specialist.validation.SpecialistFilter
@Getter
public class ExtendedSpecialistFilter extends SpecialistFilter {

        @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters.")
        protected final String firstName;

        @Size(min = 2, max = 20, message = "Second name must be between 2 and 20 characters.")
        protected final String secondName;

        @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters.")
        protected final String lastName;

        public ExtendedSpecialistFilter(String city, String cityCode, Long typeId, SpecialistLanguage lang,
                                        Integer minRating, Integer maxRating, String firstName, String secondName,
                                        String lastName, Boolean asc, Integer pageNumber, Integer pageSize) {
                super(city, cityCode, typeId, lang, minRating, maxRating, asc, pageNumber, pageSize);
                this.firstName = firstName;
                this.secondName = secondName;
                this.lastName = lastName;
        }

        public String cacheKey() {
                return ":c:" + city + ":t:" + typeId + ":mir:" + minRating + ":mar:" + maxRating +
                       ":a:" + asc + ":fn:" + firstName + ":sn:" + secondName + ":ln:" + lastName +
                       ":pn:" + pageNumber + ":ps:" + pageSize;
        }

        @Override
        public boolean isEmpty() {
                return city == null &&
                        cityCode == null &&
                        typeId == null &&
                        lang == null &&
                        minRating == null &&
                        maxRating == null &&
                        firstName == null &&
                        secondName == null &&
                        lastName == null;
        }
}
