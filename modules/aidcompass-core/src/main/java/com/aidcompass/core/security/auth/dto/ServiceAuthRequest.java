package com.aidcompass.core.security.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ServiceAuthRequest(
    @JsonProperty("service_name")
    @NotBlank(message = "Service name shouldn't be empty or blank!")
    String name,

    @JsonProperty("service_key")
    @NotBlank(message = "Service password shouldn't be empty or blank!")
    String key
) { }
