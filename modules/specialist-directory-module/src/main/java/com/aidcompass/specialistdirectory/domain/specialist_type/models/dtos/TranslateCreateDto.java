package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record TranslateCreateDto(
        @JsonIgnore
        Long typeId,
        Language language,
        String translate
) { }