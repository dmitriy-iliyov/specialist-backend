package com.aidcompass.specialistdirectory.domain.translate.models.dtos;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TranslateResponseDto(
        Long id,

        @JsonProperty("type_id")
        Long typeId,

        Language language,

        String translate
) { }
