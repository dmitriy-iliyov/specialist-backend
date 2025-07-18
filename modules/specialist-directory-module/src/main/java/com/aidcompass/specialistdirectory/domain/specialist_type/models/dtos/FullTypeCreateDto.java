package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import java.util.List;

public record FullTypeCreateDto(
        TypeCreateDto type,
        List<TranslateCreateDto> translates
) { }
