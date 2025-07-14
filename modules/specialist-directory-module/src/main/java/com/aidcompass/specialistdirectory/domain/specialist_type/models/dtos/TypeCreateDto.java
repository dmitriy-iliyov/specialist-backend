package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.aidcompass.specialistdirectory.domain.specialist_type.validate.UniqueType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@UniqueType
public class TypeCreateDto {

        @JsonIgnore
        private UUID creatorId;

        @NotBlank(message = "Title is required.")
        private final String title;

        @JsonCreator
        public TypeCreateDto(String title) {
                this.title = title;
        }
}