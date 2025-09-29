package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;

public interface SpecialistProjectionFilter extends BaseFilter {
    String getCity();
    String getCityCode();
    Long getTypeId();
    SpecialistLanguage getLang();
    Integer getMinRating();
    Integer getMaxRating();
}
