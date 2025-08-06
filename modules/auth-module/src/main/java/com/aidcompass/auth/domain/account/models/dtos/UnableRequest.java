package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.models.enums.UnableReason;
import com.aidcompass.utils.validation.annotation.ValidEnum;

public record UnableRequest(
        @ValidEnum(enumClass = UnableReason.class, message = "Unsupported unable reason.")
        UnableReason reason
) { }
