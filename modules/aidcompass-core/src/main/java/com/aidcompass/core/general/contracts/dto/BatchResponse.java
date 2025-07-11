package com.aidcompass.core.general.contracts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BatchResponse <T> (
        List<T> batch,
        @JsonProperty("has_next")
        Boolean hasNext
) { }
