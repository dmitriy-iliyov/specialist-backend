package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TranslateUpdateDto(
        Long id,

        @JsonProperty("type_id")
        Long typeId,

        Language language,

        String translate
) { }