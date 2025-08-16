package com.specialist.auth.domain.service_account.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record SecretServiceAccountResponseDto (
        String secret,
        @JsonUnwrapped
        ServiceAccountResponseDto dto
) { }
