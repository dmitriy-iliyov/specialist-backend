package com.aidcompass.specialistdirectory.domain.bookmark.models;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.UUID;

public record BookmarkResponseDto(
        UUID id,
        @JsonUnwrapped SpecialistResponseDto specialist
) { }