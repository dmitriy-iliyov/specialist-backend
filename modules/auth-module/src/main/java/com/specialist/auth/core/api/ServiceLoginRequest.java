package com.specialist.auth.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.constraints.NotBlank;

public record ServiceLoginRequest(
    @JsonProperty("client_id")
    @NotBlank(message = "Client accountId is required.")
    @ValidUuid(paramName = "client_id", message = "Client accountId should be valid.")
    String clientId,
    @JsonProperty("client_secret")
    @NotBlank(message = "Client secret is required.")
    String clientSecret
) { }
