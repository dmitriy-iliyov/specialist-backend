package com.aidcompass.specialistdirectory.domain.type.services.interfaces;

import com.aidcompass.specialistdirectory.domain.type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

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
