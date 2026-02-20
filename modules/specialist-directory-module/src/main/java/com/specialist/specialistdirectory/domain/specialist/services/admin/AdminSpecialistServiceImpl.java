package com.specialist.specialistdirectory.domain.specialist.services.admin;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistStatusService;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminSpecialistServiceImpl implements AdminSpecialistService {

    private final SpecialistService service;
    private final SpecialistStatusService statusService;
    private final AdminSpecialistAggregator aggregator;
    private final AdminSpecialistQueryService queryService;

    @Override
    public SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto) {
        dto.setCreatorId(creatorId);
        dto.setCreatorType(CreatorType.ADMIN);
        dto.setStatus(SpecialistStatus.APPROVED);
        dto.setState(SpecialistState.DEFAULT);
        return service.save(dto);
    }

    @Override
    public SpecialistResponseDto findById(UUID id) {
        return service.findById(id);
    }

    @Override
    public PageResponse<?> findAll(AdminSpecialistFilter filter) {
        if (filter.getAggregate() != null && filter.getAggregate().equals(Boolean.TRUE)) {
            return aggregator.aggregate(filter);
        }
        return queryService.findAll(filter);
    }

    @Override
    public void approve(UUID id, UUID approverId) {
        statusService.approve(id, approverId, ApproverType.ADMIN);
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        return service.update(dto);
    }

    @Override
    public void deleteById(UUID id) {
        service.deleteById(id);
    }
}
