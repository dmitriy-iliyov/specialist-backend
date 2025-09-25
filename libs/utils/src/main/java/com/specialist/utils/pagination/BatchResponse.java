package com.specialist.utils.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BatchResponse<T> (
        List<T> data,
        @JsonProperty("has_next")
        Boolean hasNext,
        @JsonProperty("next_page")
        Integer nextPage
) { }
