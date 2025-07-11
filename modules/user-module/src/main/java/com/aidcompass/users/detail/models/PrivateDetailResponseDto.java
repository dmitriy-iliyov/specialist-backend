package com.aidcompass.users.detail.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PrivateDetailResponseDto(
        Long id,

        @JsonProperty("about_myself")
        String aboutMyself,

        String address
) { }
