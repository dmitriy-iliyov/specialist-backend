package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.TranslateCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TranslateResponseDto;

public interface TranslateFacade {
    TranslateResponseDto save(TranslateCreateDto dto);
}
