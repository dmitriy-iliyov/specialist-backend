package com.specialist.specialistdirectory.domain.specialist.services.admin;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistPersistOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistStatusService;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminSpecialistFacadeImpl implements AdminSpecialistFacade {

    private final SpecialistService service;
    private final SpecialistStatusService statusService;
    private final SpecialistPersistOrchestrator persistOrchestrator;
    private final AdminSpecialistQueryOrchestrator queryOrchestrator;

    @Override
    public SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto) {
        return persistOrchestrator.save(creatorId, CreatorType.ADMIN, dto);
    }

    @Override
    public SpecialistResponseDto findById(UUID id) {
        return service.findById(id);
    }

    @Override
    public PageResponse<?> findAll(AdminSpecialistFilter filter) {
        return queryOrchestrator.findAll(filter);
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
