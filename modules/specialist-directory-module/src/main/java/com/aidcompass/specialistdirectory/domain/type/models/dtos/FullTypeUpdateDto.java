package com.aidcompass.specialistdirectory.domain.type.models.dtos;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.CompositeTranslateUpdateDto;
import com.aidcompass.specialistdirectory.domain.type.validation.annotation.SynchronizedTypeId;
import com.aidcompass.specialistdirectory.domain.type.validation.annotation.TranslateList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@SynchronizedTypeId
public record FullTypeUpdateDto(
        @NotNull(message = "Type is required.")
        @Valid TypeUpdateDto type,

        @TranslateList
        @NotNull(message = "At least translate is required.")
        @Valid List<CompositeTranslateUpdateDto> translates
) { }