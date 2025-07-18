package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.*;

import java.util.List;

public interface TypeOrchestrator {
    FullTypeResponseDto save(FullTypeCreateDto dto);

    FullTypeResponseDto update(FullTypeUpdateDto dto);

    void deleteById(Long id);

    List<FullTypeResponseDto> findAll();

    List<FullTypeResponseDto> findAllUnapproved();
}
