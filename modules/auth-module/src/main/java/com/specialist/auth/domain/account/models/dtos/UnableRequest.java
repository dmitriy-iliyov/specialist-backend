package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.domain.account.models.enums.UnableReason;
import com.specialist.utils.validation.annotation.ValidEnum;

public record UnableRequest(
        @ValidEnum(enumClass = UnableReason.class, message = "Unsupported unable reason.")
        UnableReason reason
) { }
