package com.specialist.specialistdirectory.domain.bookmark.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.utils.validation.annotation.UUIDv7;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class BookmarkCreateDto {
    @JsonIgnore
    private UUID ownerId;

    @JsonProperty("specialist_id")
    @NotNull(message = "Specialist id is required.")
    @UUIDv7(paramName = "specialist_id")
    private final UUID specialistId;


    @JsonCreator
    public BookmarkCreateDto(UUID specialistId) {
        this.specialistId = specialistId;
    }
}
