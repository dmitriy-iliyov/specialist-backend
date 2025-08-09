package com.specialist.specialistdirectory.domain.translate.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.language.Language;

public record TranslateResponseDto(
        Long id,

        @JsonProperty("type_id")
        Long typeId,

        Language language,

        String translate
) { }
