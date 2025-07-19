package com.aidcompass.specialistdirectory.domain.type.models.dtos;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.CompositeTranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.type.validation.annotation.TranslateList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FullTypeCreateDto(
        @NotNull(message = "Type is required.")
        @Valid TypeCreateDto type,

        @TranslateList
        @NotNull(message = "At least one translate is required.")
        @Valid List<CompositeTranslateCreateDto> translates
) { }
