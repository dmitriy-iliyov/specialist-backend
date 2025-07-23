package com.aidcompass.specialistdirectory.domain.translate.services;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateResponseDto;
import com.aidcompass.specialistdirectory.domain.type.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslateOrchestratorImpl implements TranslateOrchestrator {

    private final TranslateService translateService;
    private final TypeService typeService;


    @Override
    public TranslateResponseDto save(TranslateCreateDto dto) {
        return translateService.save(typeService.getReferenceById(dto.getTypeId()), dto);
    }
}
