package com.aidcompass.specialistdirectory.domain.review.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class ReviewUpdateDto {

    @JsonIgnore
    private UUID id;

    @JsonIgnore
    private UUID creatorId;

    @NotBlank(message = "Description is required.")
    private final String description;

    @NotNull(message = "Rating is required.")
    private final Long rating;

    private UUID specialistId;
}
