package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FullTypeUpdateDto(
        @NotNull(message = "Type is required.")
        @Valid TypeUpdateDto type,

        @NotNull(message = "At least translate is required.")
        @Valid List<TranslateUpdateDto> translates
) { }