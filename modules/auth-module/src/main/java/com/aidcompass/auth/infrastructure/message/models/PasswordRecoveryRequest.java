package com.aidcompass.auth.infrastructure.message.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordRecoveryRequest(
        @NotBlank(message = "Code is required.")
        @Pattern(regexp = "^//d{6}$", message = "Invalid code.")
        String code,
        @NotBlank(message = "Password is required.")
        @Size(min = 10, max = 50, message = "Password length must be greater than 10 and less than 50!")
        String password
) { }