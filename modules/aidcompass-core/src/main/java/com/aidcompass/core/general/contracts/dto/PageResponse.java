package com.aidcompass.core.general.contracts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PageResponse<T>(
    List<T> data,
    @JsonProperty("total_page")
    int totalPage
) { }
