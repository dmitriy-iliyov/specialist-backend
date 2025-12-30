package com.specialist.auth.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ServiceLoginRequest(
    @JsonProperty("client_id")
    @NotBlank(message = "Client accountId is required.")
    String clientId,
    @JsonProperty("client_secret")
    @NotBlank(message = "Client secret is required.")
    String clientSecret
) { }
