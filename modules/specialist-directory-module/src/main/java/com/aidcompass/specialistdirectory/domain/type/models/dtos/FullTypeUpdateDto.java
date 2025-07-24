package com.aidcompass.specialistdirectory.domain.type.models.dtos;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.CompositeTranslateUpdateDto;
import com.aidcompass.specialistdirectory.domain.type.validation.SynchronizedTypeId;
import com.aidcompass.specialistdirectory.domain.type.validation.TranslateList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@SynchronizedTypeId
public record FullTypeUpdateDto(
        @NotNull(message = "Type is required.")
        @Valid TypeUpdateDto type,

        @TranslateList
        @NotEmpty(message = "At least one translate is required.")
        @Valid List<CompositeTranslateUpdateDto> translates
) { }