package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.contracts.profile.SystemSpecialistProfileService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import com.specialist.specialistdirectory.exceptions.UnexpectedNonManagedSpecialistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SelfSpecialistOrchestratorImpl implements SelfSpecialistOrchestrator {

    private final SpecialistService service;
    private final SystemSpecialistProfileService specialistProfileService;

    @Transactional
    @Override
    public SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto) {
        dto.setCreatorId(creatorId);
        dto.setCreatorType(CreatorType.SPECIALIST);
        dto.setStatus(SpecialistStatus.UNAPPROVED);
        SpecialistResponseDto responseDto = service.save(dto);
        specialistProfileService.setSpecialistCardId(responseDto.getId());
        return responseDto;
    }

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
