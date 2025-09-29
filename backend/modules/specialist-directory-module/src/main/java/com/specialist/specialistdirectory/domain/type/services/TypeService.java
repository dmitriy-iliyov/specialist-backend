package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.List;


public interface TypeService {

    TypeResponseDto save(TypeCreateDto dto);

    Long saveSuggested(TypeCreateDto dto);

    String approve(Long id);

    TypeResponseDto update(TypeUpdateDto dto);

    boolean existsByTitleIgnoreCase(String title);

    TypeEntity getReferenceById(Long id);

    List<Long> findAllApprovedIds();

    boolean existsById(Long id);

    PageResponse<TypeResponseDto> findAllUnapproved(PageRequest page);

    void deleteById(Long id);

    TypeResponseDto findSuggestedById(Long id);

    ShortTypeResponseDto findByTitle(String title);

    PageResponse<TypeResponseDto> findAll(PageRequest page);
}
