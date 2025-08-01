package com.aidcompass.auth.domain.account.models;

import com.aidcompass.auth.domain.account.models.enums.LockReasonType;
import com.aidcompass.auth.domain.account.models.enums.UnableReasonType;
import com.aidcompass.utils.pagination.PageDataHolder;
import com.aidcompass.utils.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AccountFilter(
        Boolean locked,

        @ValidEnum(enumClass = LockReasonType.class, nullable = true, message = "Unknown lock reason.")
        String lockReason,

        Boolean enable,

        @ValidEnum(enumClass = UnableReasonType.class, nullable = true, message = "Unknown lock reason.")
        String unableReason,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize,

        Boolean asc
) implements PageDataHolder { }
