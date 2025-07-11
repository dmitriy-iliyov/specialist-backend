package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.exceptions.OwnershipException;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SpecialistFacadeImpl implements SpecialistFacade {

    private final SpecialistService specialistService;


    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        assertOwnership(dto.getCreatorId(), dto.getId());
        return specialistService.update(dto);
    }

    @Override
    public void delete(UUID creatorId, UUID id) {
        assertOwnership(creatorId, id);
        specialistService.deleteById(id);
    }

    private void assertOwnership(UUID creatorId, UUID id) {
        UUID realCreatorId = specialistService.getCreatorIdById(id);
        if (!realCreatorId.equals(creatorId)) {
            throw new OwnershipException();
        }
    }
}