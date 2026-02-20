package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.PaginationUtils;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistCountServiceImpl implements SpecialistCountService {

    private final SpecialistRepository repository;

    @Cacheable(value = "specialists:count:total")
    @Transactional(readOnly = true)
    @Override
    public long countAll() {
        Specification<SpecialistEntity> specification = Specification.where(
                SpecialistSpecification.filterByStatus(SpecialistStatus.APPROVED)
        );
        return repository.count(specification);
    }

    @Cacheable(value = "specialists:count:filter", key = "#filter.cacheKey()")
    @Transactional(readOnly = true)
    @Override
    public long countByFilter(SpecialistFilter filter) {
        return repository.count(PaginationUtils.generateSpecification(filter));
    }

    @Cacheable(value = "specialists:created:count:total", key = "#creatorId")
    @Transactional(readOnly = true)
    @Override
    public long countByCreatorId(UUID creatorId) {
        return repository.countByCreatorId(creatorId);
    }

    @Cacheable(value = "specialists:created:count:filter", key = "#creatorId + ':' + #filter.cacheKey()")
    @Transactional(readOnly = true)
    @Override
    public long countByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter) {
        Specification<SpecialistEntity> specification = PaginationUtils
                .generateSpecification(filter)
                .and(SpecialistSpecification.filterByCreatorId(creatorId));
        return repository.count(specification);
    }

    @Transactional(readOnly = true)
    @Override
    public long countByAdminFilter(AdminSpecialistFilter filter) {
        return repository.count(PaginationUtils.generateSpecification(filter));
    }
}