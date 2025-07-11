package com.aidcompass.message.pass_recovery;

import jakarta.validation.constraints.NotBlank;

public record RecoveryPair(
        @NotBlank(message = "Code shouldn't be blank or empty!")
        String code,

        @NotBlank(message = "Password shouldn't be blank or empty!")
        String password
) { }
