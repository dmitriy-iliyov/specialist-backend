package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.utils.validation.annotation.ValidEnum;

public record DisableRequest(
        @ValidEnum(enumClass = DisableReason.class, message = "Unsupported disable reason.")
        DisableReason reason
) { }
