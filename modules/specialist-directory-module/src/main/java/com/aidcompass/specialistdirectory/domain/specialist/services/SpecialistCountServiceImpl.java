package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecificationFabric;
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
        return repository.count();
    }

    //@Cacheable(value = "specialists:count:filter", key = "#filter.cacheKey()")
    @Transactional(readOnly = true)
    @Override
    public long countByFilter(SpecialistFilter filter) {
        return repository.count(SpecificationFabric.generateSpecification(filter));
    }

    //@Cacheable(value = "specialists:count:created:total", key = "#creatorId")
    @Override
    @Transactional(readOnly = true)
    public long countByCreatorId(UUID creatorId) {
        return repository.countByCreatorId(creatorId);
    }

    //@Cacheable(value = "specialists:count:created", key = "#creatorId + ':' + #filter.cacheKey()")
    @Override
    @Transactional(readOnly = true)
    public long countByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter) {
        Specification<SpecialistEntity> specification = SpecificationFabric
                .generateSpecification(filter)
                .and(SpecialistSpecification.filterByCreatorId(creatorId));
        return repository.count(specification);
    }
}