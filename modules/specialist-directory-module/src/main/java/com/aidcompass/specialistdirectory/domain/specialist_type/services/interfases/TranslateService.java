package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TranslateService {
    List<TranslateResponseDto> saveAll(TypeEntity type, List<TranslateCreateDto> dtoList);

    List<TranslateResponseDto> updateAll(TypeEntity type, List<TranslateUpdateDto> dtoList);

    List<TranslateResponseDto> findAllByTypeId(Long typeId);

    List<ShortTypeResponseDto> findAllApprovedAsJson(Language language);

    Map<Long, String> findAllApprovedAsMap(Language language);

    Map<Long, List<TranslateResponseDto>> findAllByIdIn(Set<Long> ids);
}
