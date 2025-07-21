package com.aidcompass.specialistdirectory.domain.type.services.interfaces;

import com.aidcompass.specialistdirectory.domain.type.models.dtos.FullTypeCreateDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.FullTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.FullTypeUpdateDto;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface TypeOrchestrator {
    FullTypeResponseDto save(UUID creatorId, FullTypeCreateDto dto);

    FullTypeResponseDto update(Long typeId, FullTypeUpdateDto dto);

    void deleteById(Long id);

    PageResponse<FullTypeResponseDto> findAll(PageRequest page);

    PageResponse<FullTypeResponseDto> findAllUnapproved(PageRequest page);
}
