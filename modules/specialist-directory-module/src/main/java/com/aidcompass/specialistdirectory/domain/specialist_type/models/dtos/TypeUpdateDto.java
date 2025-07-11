package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TypeUpdateDto {

    @NotNull(message = "Id is required.")
    @Positive(message = "Id should be positive.")
    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    @JsonProperty("is_approved")
    private boolean isApproved;
}
