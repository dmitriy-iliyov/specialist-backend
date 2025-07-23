package com.aidcompass.specialistdirectory.domain.translate.services.interfaces;

import com.aidcompass.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface TypeTranslateOrchestrator {
    Map<Long, String> findAllApprovedAsMap(HttpServletRequest request);

    List<ShortTypeResponseDto> findAllApprovedAsJson(HttpServletRequest request);
}
