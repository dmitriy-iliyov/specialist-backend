package com.aidcompass.specialistdirectory.domain.specialist.repositories;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationFabric {

    public static Specification<SpecialistEntity> generateSpecification(BaseSpecialistFilter filter) {
        return Specification.where(SpecialistSpecification.filterByCity(filter.city()))
                .and(SpecialistSpecification.filterByType(filter.typeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.minRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.maxRating()));
    }
}
