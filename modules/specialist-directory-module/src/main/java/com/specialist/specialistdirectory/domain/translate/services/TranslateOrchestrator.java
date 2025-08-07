package com.specialist.specialistdirectory.domain.translate.services;

import com.specialist.specialistdirectory.domain.translate.models.dtos.TranslateCreateDto;
import com.specialist.specialistdirectory.domain.translate.models.dtos.TranslateResponseDto;

public interface TranslateOrchestrator {
    TranslateResponseDto save(TranslateCreateDto dto);
}
