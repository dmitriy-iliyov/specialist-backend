package com.aidcompass.specialistdirectory.domain.translate.services;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateResponseDto;

public interface TranslateOrchestrator {
    TranslateResponseDto save(TranslateCreateDto dto);
}
