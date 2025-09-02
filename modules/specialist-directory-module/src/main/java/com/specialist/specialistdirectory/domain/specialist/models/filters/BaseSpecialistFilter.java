package com.specialist.specialistdirectory.domain.specialist.models.filters;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;

public interface BaseSpecialistFilter {
    String city();
    String cityCode();
    Long typeId();
    SpecialistLanguage lang();
    Integer minRating();
    Integer maxRating();
}
