package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;

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

    List<TypeResponseDto> findAllUnapproved();

    void deleteById(Long id);

    TypeResponseDto findSuggestedById(Long id);

    ShortTypeResponseDto findByTitle(String title);

    List<TypeResponseDto> findAll();
}
