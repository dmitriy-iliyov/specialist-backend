package com.specialist.specialistdirectory.domain.specialist.models.markers;

public interface BaseSpecialistFilter {
    String city();
    String cityCode();
    Long typeId();
    Integer minRating();
    Integer maxRating();
}
