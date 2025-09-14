package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSpecialistQueryOrchestratorImpl implements AdminSpecialistQueryOrchestrator {

    private final AdminSpecialistAggregator aggregator;
    private final AdminSpecialistQueryService queryService;

    @Override
    public PageResponse<?> findAll(AdminSpecialistFilter filter) {
        if (filter.getAggregate() != null && filter.getAggregate().equals(Boolean.TRUE)) {
            return aggregator.aggregate(filter);
        }
        return queryService.findAll(filter);
    }
}
