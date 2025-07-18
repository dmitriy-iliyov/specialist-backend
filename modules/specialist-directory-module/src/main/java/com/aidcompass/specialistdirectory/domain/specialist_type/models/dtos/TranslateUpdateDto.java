package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TranslateUpdateDto(
        @NotNull(message = "Id is required.")
        @PositiveOrZero(message = "Id should be positive or zero.")
        Long id,

        @JsonProperty("type_id")
        Long typeId,

        Language language,

        @NotBlank(message = "Translate is required.")
        String translate
) { }