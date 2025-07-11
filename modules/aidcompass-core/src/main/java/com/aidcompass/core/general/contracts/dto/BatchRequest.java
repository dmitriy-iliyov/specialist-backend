package com.aidcompass.core.general.contracts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record BatchRequest(
        @JsonProperty("batch_size")
        @Positive(message = "Batch size should be positive!")
        int size,

        @PositiveOrZero(message = "Page number must be zero or positive")
        int page
) { }
