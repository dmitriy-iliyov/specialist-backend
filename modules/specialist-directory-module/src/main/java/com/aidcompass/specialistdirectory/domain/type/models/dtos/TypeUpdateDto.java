package com.aidcompass.specialistdirectory.domain.type.models.dtos;

import com.aidcompass.specialistdirectory.domain.type.validation.annotation.UniqueType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@UniqueType
public class TypeUpdateDto {

    private Long id;

    @NotBlank(message = "Title is required.")
    private final String title;

    @JsonProperty("is_approved")
    private final boolean isApproved;
}