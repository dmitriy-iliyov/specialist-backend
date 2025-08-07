package com.specialist.auth.domain.account.models;

import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.models.enums.UnableReason;
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

        @ValidEnum(enumClass = UnableReason.class, nullable = true, message = "Unknown unable reason.")
        String unableReason,

        @NotNull(message = "Page number is required.")
        @PositiveOrZero(message = "Page number should be positive or zero.")
        Integer pageNumber,

        @NotNull(message = "Page size is required.")
        @Min(value = 10, message = "Min page size is 10.")
        Integer pageSize,

        Boolean asc
) implements PageDataHolder { }
