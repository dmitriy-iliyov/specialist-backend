package com.specialist.specialistdirectory.domain.type.models.dtos;

import com.specialist.specialistdirectory.domain.translate.models.dtos.TranslateResponseDto;

import java.util.List;

public record FullTypeResponseDto(
        TypeResponseDto type,
        List<TranslateResponseDto> translates
) { }
