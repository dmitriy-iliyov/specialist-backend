package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.domain.account.models.enums.DisableReason;

public record DisableRequest(
        DisableReason reason
) { }
