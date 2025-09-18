package com.specialist.specialistdirectory.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistProjectionFilter;
import com.specialist.utils.pagination.PageDataHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


public class PaginationUtils {

    public static Specification<SpecialistEntity> generateSpecification(SpecialistProjectionFilter filter) {
        return Specification.where(SpecialistSpecification.filterByApprovedAndManaged())
                .and(SpecialistSpecification.filterByCity(filter.getCity()))
                .and(SpecialistSpecification.filterByCityCode(filter.getCityCode()))
                .and(SpecialistSpecification.filterByType(filter.getTypeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.getMinRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.getMaxRating()))
                .and(SpecialistSpecification.filterByLanguage(filter.getLang()));
    }

    public static Specification<SpecialistEntity> generateSpecification(ExtendedSpecialistFilter filter) {
        return Specification.where(SpecialistSpecification.filterByCity(filter.getCity()))
                .and(SpecialistSpecification.filterByCityCode(filter.getCityCode()))
                .and(SpecialistSpecification.filterByType(filter.getTypeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.getMinRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.getMaxRating()))
                .and(SpecialistSpecification.filterByFirstName(filter.getFirstName()))
                .and(SpecialistSpecification.filterBySecondName(filter.getSecondName()))
                .and(SpecialistSpecification.filterByLastName(filter.getLastName()))
                .and(SpecialistSpecification.filterByLanguage(filter.getLang()));
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
