package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.models.enums.LockReasonType;
import com.aidcompass.utils.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record LockDto(
    @ValidEnum(enumClass = LockReasonType.class, message = "Unsupported lock reason.")
    LockReasonType reason,
    @NotNull(message = "Lock term is required.")
    Instant term
) { }
