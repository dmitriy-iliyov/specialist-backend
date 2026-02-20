package com.specialist.specialistdirectory.domain.type.models.dtos;

import java.util.List;

public record FullTypeResponseDto(
        TypeResponseDto type,
        List<TranslateResponseDto> translates
) { }
