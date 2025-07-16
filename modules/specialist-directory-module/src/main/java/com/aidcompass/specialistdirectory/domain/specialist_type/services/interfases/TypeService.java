package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;

import java.util.List;
import java.util.Map;


public interface TypeService {

    TypeDto save(TypeCreateDto dto);

    Long saveSuggested(TypeCreateDto dto);

    String approve(Long id);

    TypeDto update(TypeUpdateDto dto);

    boolean existsByTitleIgnoreCase(String title);

    TypeEntity getReferenceById(Long id);

    List<ShortTypeDto> findAllApprovedAsJson();

    Map<Long, String> findAllApprovedAsMap();

    boolean existsById(Long id);

    List<TypeDto> findAllUnapproved();

    void deleteById(Long id);

    TypeDto findSuggestedById(Long id);

    ShortTypeDto findByTitle(String title);

    List<TypeDto> findAll();
}
