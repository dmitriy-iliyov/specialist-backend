package com.aidcompass.specialistdirectory.domain.translate.services.interfaces;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.*;
import com.aidcompass.specialistdirectory.domain.type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TranslateService {
    TranslateResponseDto save(TypeEntity type, TranslateCreateDto dto);

    List<TranslateResponseDto> saveAll(TypeEntity type, List<CompositeTranslateCreateDto> dtoList);

    List<TranslateResponseDto> updateAll(TypeEntity type, List<CompositeTranslateUpdateDto> dtoList);

    List<ShortTypeResponseDto> findAllApprovedAsJson(Language language);

    Map<Long, String> findAllApprovedAsMap(Language language);

    Map<Long, List<TranslateResponseDto>> findAllByIdIn(Set<Long> ids);

    TranslateResponseDto update(TranslateUpdateDto dto);

    void deleteById(Long id);
}
