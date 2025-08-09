package com.specialist.auth.domain.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.utils.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LockRequest(
    @ValidEnum(enumClass = LockReason.class, message = "Unsupported lock reason.")
    LockReason reason,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Lock term is required.")
    LocalDateTime term
) { }
