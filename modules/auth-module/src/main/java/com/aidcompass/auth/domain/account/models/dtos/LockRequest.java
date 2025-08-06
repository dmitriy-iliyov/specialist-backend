package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.models.enums.LockReason;
import com.aidcompass.utils.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record LockRequest(
    @ValidEnum(enumClass = LockReason.class, message = "Unsupported lock reason.")
    LockReason reason,
    @NotNull(message = "Lock term is required.")
    Instant term
) { }
