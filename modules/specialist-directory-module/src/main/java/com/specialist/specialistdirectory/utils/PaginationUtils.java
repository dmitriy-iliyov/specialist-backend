package com.specialist.specialistdirectory.utils;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import com.specialist.utils.pagination.PageDataHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


public class PaginationUtils {

    public static Specification<SpecialistEntity> generateSpecification(BaseSpecialistFilter filter) {
        return Specification.where(SpecialistSpecification.filterByApproved(true))
                .and(SpecialistSpecification.filterByCity(filter.city()))
                .and(SpecialistSpecification.filterByCityCode(filter.cityCode()))
                .and(SpecialistSpecification.filterByType(filter.typeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.minRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.maxRating()))
                .and(SpecialistSpecification.filterByLanguage(filter.lang()));
    }

    public static Specification<SpecialistEntity> generateSpecification(ExtendedSpecialistFilter filter) {
        return Specification.where(SpecialistSpecification.filterByCity(filter.city()))
                .and(SpecialistSpecification.filterByCityCode(filter.cityCode()))
                .and(SpecialistSpecification.filterByType(filter.typeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.minRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.maxRating()))
                .and(SpecialistSpecification.filterByFirstName(filter.firstName()))
                .and(SpecialistSpecification.filterBySecondName(filter.secondName()))
                .and(SpecialistSpecification.filterByLastName(filter.lastName()))
                .and(SpecialistSpecification.filterByLanguage(filter.lang()));
    }

    public static Pageable generatePageable(PageDataHolder holder) {
        if (holder.asc() != null && holder.asc()) {
            return org.springframework.data.domain.PageRequest
                    .of(holder.pageNumber(), holder.pageSize(), Sort.by("rating").ascending());
        }
        return org.springframework.data.domain.PageRequest
                .of(holder.pageNumber(), holder.pageSize(), Sort.by("rating").descending());
    }
}
