package com.specialist.specialistdirectory.domain.type.models.dtos;

import com.specialist.specialistdirectory.domain.type.validation.TranslateList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FullTypeCreateDto(
        @NotNull(message = "Type is required.")
        @Valid TypeCreateDto type,

        @TranslateList
        @NotEmpty(message = "At least one translate is required.")
        @Valid List<CompositeTranslateCreateDto> translates
) { }
