package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import java.util.List;

public record FullTypeUpdateDto(
        TypeUpdateDto type,
        List<TranslateUpdateDto> translates
) { }
