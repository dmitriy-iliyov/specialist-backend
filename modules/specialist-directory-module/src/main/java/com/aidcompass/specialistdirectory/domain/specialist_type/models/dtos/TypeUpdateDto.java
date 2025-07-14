package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.aidcompass.specialistdirectory.domain.specialist_type.validate.UniqueType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
