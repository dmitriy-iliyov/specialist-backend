package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.TranslateCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TranslateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslateFacadeImpl implements TranslateFacade {

    private final TranslateService translateService;
    private final TypeService typeService;

    @Override
    public TranslateResponseDto save(TranslateCreateDto dto) {
        return translateService.save(typeService.getReferenceById(dto.getTypeId()), dto);
    }
}
