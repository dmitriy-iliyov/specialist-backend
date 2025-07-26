package com.aidcompass.specialistdirectory.domain.type.services;

import com.aidcompass.specialistdirectory.domain.type.models.dtos.FullTypeCreateDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.FullTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.FullTypeUpdateDto;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.pagination.PageResponse;

import java.util.UUID;

public interface TypeOrchestrator {
    FullTypeResponseDto save(UUID creatorId, FullTypeCreateDto dto);

    FullTypeResponseDto update(Long typeId, FullTypeUpdateDto dto);

    void deleteById(Long id);

    PageResponse<FullTypeResponseDto> findAll(PageRequest page);

    PageResponse<FullTypeResponseDto> findAllUnapproved(PageRequest page);
}
