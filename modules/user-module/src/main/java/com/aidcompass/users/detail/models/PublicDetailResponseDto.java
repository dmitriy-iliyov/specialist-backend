package com.aidcompass.users.detail.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublicDetailResponseDto(
        @JsonProperty("about_myself")
        String aboutMyself,

        String address
) { }
