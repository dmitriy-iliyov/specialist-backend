package com.aidcompass.core.general.exceptions.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDto(
        @JsonProperty("field")
        String field,
        @JsonProperty("message")
        String message
) { }