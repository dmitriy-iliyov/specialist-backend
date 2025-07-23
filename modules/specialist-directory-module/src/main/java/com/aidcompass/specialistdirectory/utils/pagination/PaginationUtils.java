package com.aidcompass.specialistdirectory.utils.pagination;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class PaginationUtils {

    public static Specification<SpecialistEntity> generateSpecification(BaseSpecialistFilter filter) {
        return Specification.where(SpecialistSpecification.filterByCity(filter.city()))
                .and(SpecialistSpecification.filterByCityCode(filter.cityCode()))
                .and(SpecialistSpecification.filterByType(filter.typeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.minRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.maxRating()));
    }

    public static Specification<SpecialistEntity> generateSpecification(ExtendedSpecialistFilter filter) {
        return Specification.where(SpecialistSpecification.filterByCity(filter.city()))
                .and(SpecialistSpecification.filterByCityCode(filter.cityCode()))
                .and(SpecialistSpecification.filterByType(filter.typeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.minRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.maxRating()))
                .and(SpecialistSpecification.filterByFirstName(filter.firstName()))
                .and(SpecialistSpecification.filterBySecondName(filter.secondName()))
                .and(SpecialistSpecification.filterByLastName(filter.lastName()));
    }

    public static Pageable generatePageable(PageDataHolder holder) {
        if (holder.asc() != null && holder.asc()) {
            return org.springframework.data.domain.PageRequest
                    .of(holder.pageNumber(), holder.pageSize(), Sort.by("totalRating").ascending());
        }
        return org.springframework.data.domain.PageRequest
                .of(holder.pageNumber(), holder.pageSize(), Sort.by("totalRating").descending());
    }
}
