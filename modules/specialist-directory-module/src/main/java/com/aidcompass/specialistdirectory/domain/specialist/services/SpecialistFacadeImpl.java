package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.exceptions.OwnershipException;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SpecialistFacadeImpl implements SpecialistFacade {

    private final SpecialistService specialistService;
    private final BookmarkService bookmarkService;


    @Transactional
    @Override
    public SpecialistResponseDto save(SpecialistCreateDto dto) {
        SpecialistResponseDto responseDto = specialistService.save(dto);
        bookmarkService.save(responseDto.getCreatorId(), responseDto.getId());
        return responseDto;
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        assertOwnership(dto.getCreatorId(), dto.getId());
        return specialistService.update(dto);
    }

    @Transactional
    @Override
    public void delete(UUID creatorId, UUID id) {
        assertOwnership(creatorId, id);
        specialistService.deleteById(id);
        bookmarkService.deleteBySpecialistId(creatorId, id);
    }

    private void assertOwnership(UUID creatorId, UUID id) {
        UUID realCreatorId = specialistService.getCreatorIdById(id);
        if (!realCreatorId.equals(creatorId)) {
            throw new OwnershipException();
        }
    }
}