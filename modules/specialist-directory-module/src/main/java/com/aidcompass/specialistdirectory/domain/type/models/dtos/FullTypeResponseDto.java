package com.aidcompass.specialistdirectory.domain.type.models.dtos;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateResponseDto;

import java.util.List;

public record FullTypeResponseDto(
        TypeResponseDto type,
        List<TranslateResponseDto> translates
) { }
