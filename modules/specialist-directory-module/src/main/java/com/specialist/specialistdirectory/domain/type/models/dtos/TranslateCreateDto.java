package com.specialist.specialistdirectory.domain.type.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.specialist.specialistdirectory.domain.language.Language;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TranslateCreateDto {

    @JsonIgnore
    private Long typeId;

    private final Language language;

    @NotBlank(message = "Translate is required.")
    private final String translate;
}
