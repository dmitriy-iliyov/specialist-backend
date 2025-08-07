package com.specialist.specialistdirectory.domain.translate.models.dtos;

import com.specialist.specialistdirectory.domain.language.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TranslateResponseDto(
        Long id,

        @JsonProperty("type_id")
        Long typeId,

        Language language,

        String translate
) { }
