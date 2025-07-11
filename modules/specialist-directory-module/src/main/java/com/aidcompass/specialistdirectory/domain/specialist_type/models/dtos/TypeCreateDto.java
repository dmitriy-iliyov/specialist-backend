package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TypeCreateDto {

        @JsonIgnore
        private UUID creatorId;

        @NotBlank(message = "Title is required.")
        private final String title;
}
