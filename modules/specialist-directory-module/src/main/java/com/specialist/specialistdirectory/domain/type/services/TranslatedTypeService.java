package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface TranslatedTypeService {
    Map<Long, String> findAllApprovedAsMap(HttpServletRequest request);

    List<ShortTypeResponseDto> findAllApprovedAsJson(HttpServletRequest request);
}
