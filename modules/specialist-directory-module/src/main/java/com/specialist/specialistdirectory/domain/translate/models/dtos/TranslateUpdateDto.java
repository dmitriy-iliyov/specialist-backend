package com.specialist.specialistdirectory.domain.translate.models.dtos;

import com.specialist.specialistdirectory.domain.language.Language;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TranslateUpdateDto {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long typeId;

    private final Language language;

    @NotBlank(message = "Translate is required.")
    private final String translate;
}
