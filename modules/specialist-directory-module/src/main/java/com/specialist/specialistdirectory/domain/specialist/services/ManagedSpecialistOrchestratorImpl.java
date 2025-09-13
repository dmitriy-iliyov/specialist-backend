package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import com.specialist.specialistdirectory.exceptions.UnexpectedNonManagedSpecialistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagedSpecialistOrchestratorImpl implements ManagedSpecialistOrchestrator {

    private final SpecialistService service;

    @Transactional
    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        validate(dto.getAccountId(), dto.getId());
        return service.update(dto);
    }

    @Transactional
    @Override
    public void delete(UUID accountId, UUID id) {
        validate(accountId, id);
        service.deleteById(id);
    }

    private void validate(UUID accountId, UUID id) {
        ShortSpecialistInfo info = service.getShortInfoById(id);
        if (!info.status().equals(SpecialistStatus.MANAGED)) {
            throw new UnexpectedNonManagedSpecialistException();
        }
        if (!info.ownerId().equals(accountId)) {
            throw new OwnershipException();
        }
    }
}
