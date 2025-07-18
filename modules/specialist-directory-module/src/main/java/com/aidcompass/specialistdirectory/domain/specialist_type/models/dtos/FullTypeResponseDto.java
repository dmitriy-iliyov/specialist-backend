package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import java.util.List;

public record FullTypeResponseDto(
        TypeResponseDto type,
        List<TranslateResponseDto> translates
) { }
