package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record TypeCreateDto(
        @NotBlank(message = "Title is required.")
        String title
) { }
