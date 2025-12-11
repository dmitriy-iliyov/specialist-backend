package com.specialist.specialistdirectory.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistProjectionFilter;
import com.specialist.specialistdirectory.exceptions.UnexpectedSpecialistFilterStateException;
import com.specialist.utils.pagination.PageDataHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


public class PaginationUtils {

    public static Specification<SpecialistEntity> generateSpecification(SpecialistProjectionFilter filter) {
        return SpecialistSpecification.filterByStatus(SpecialistStatus.APPROVED)
                .and(SpecialistSpecification.filterByCity(filter.getCity()))
                .and(SpecialistSpecification.filterByCityCode(filter.getCityCode()))
                .and(SpecialistSpecification.filterByType(filter.getTypeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.getMinRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.getMaxRating()))
                .and(SpecialistSpecification.filterByLanguage(filter.getLang()));
    }

    public static Specification<SpecialistEntity> generateSpecification(ExtendedSpecialistFilter filter) {
        Specification<SpecialistEntity> specification;
        if (filter.getCityCode() != null) {
            specification = SpecialistSpecification.filterByCityCode(filter.getCityCode());
        } else if (filter.getCity() != null) {
            specification = SpecialistSpecification.filterByCity(filter.getCity());
        } else {
            throw new UnexpectedSpecialistFilterStateException();
        }
        return specification.and(SpecialistSpecification.filterByType(filter.getTypeId()))
                .and(SpecialistSpecification.filerByMinRating(filter.getMinRating()))
                .and(SpecialistSpecification.filterByMaxRating(filter.getMaxRating()))
                .and(SpecialistSpecification.filterByFirstName(filter.getFirstName()))
                .and(SpecialistSpecification.filterBySecondName(filter.getSecondName()))
                .and(SpecialistSpecification.filterByLastName(filter.getLastName()))
                .and(SpecialistSpecification.filterByLanguage(filter.getLang()));
    }

    public static Specification<SpecialistEntity> generateSpecification(AdminSpecialistFilter filter) {
        return generateSpecification((ExtendedSpecialistFilter) filter)
                .and(SpecialistSpecification.filterByStatus(filter.getStatus()))
                .and(SpecialistSpecification.filterByState(filter.getState()));
    }

    public static Pageable generatePageable(PageDataHolder holder) {
        if (holder.isAsc() != null && holder.isAsc()) {
            return org.springframework.data.domain.PageRequest
                    .of(holder.getPageNumber(), holder.getPageSize(), Sort.by("rating").ascending());
        }
        return org.springframework.data.domain.PageRequest
                .of(holder.getPageNumber(), holder.getPageSize(), Sort.by("rating").descending());
    }
}
