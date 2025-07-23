package com.aidcompass.specialistdirectory.domain.bookmark.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-7[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$\"",
            message = "Invalid UUID format.")
    private final UUID specialistId;


    @JsonCreator
    public BookmarkCreateDto(UUID specialistId) {
        this.specialistId = specialistId;
    }
}
