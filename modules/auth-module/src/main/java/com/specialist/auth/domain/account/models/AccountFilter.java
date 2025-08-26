package com.specialist.auth.domain.account.models;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AccountFilter(
        Boolean locked,

        @ValidEnum(enumClass = LockReason.class, nullable = true, message = "Unknown lock reason.")
        String lockReason,

        Boolean enable,

        @ValidEnum(enumClass = DisableReason.class, nullable = true, message = "Unknown disable reason.")
        String disableReason,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize,

        Boolean asc
) implements PageDataHolder { }
