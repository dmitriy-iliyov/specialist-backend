package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeUpdateDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface TypeOrchestrator {
    FullTypeResponseDto save(UUID creatorId, FullTypeCreateDto dto);

    FullTypeResponseDto update(Long typeId, FullTypeUpdateDto dto);

    void deleteById(Long id);

    PageResponse<FullTypeResponseDto> findAll(PageRequest page);

    PageResponse<FullTypeResponseDto> findAllUnapproved(PageRequest page);
}
