package com.specialist.specialistdirectory.domain.bookmark.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.BookmarkSpecialistResponseDto;

import java.util.UUID;

public record BookmarkResponseDto(
        UUID id,
        @JsonUnwrapped BookmarkSpecialistResponseDto specialist
) { }