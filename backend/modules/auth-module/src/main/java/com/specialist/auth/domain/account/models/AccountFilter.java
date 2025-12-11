package com.specialist.auth.domain.account.models;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class AccountFilter extends PageRequest implements PageDataHolder {

        private final Boolean locked;

        @ValidEnum(enumClass = LockReason.class, nullable = true, message = "Unknown lock reason.")
        private final String lockReason;

        private final Boolean enable;

        @ValidEnum(enumClass = DisableReason.class, nullable = true, message = "Unknown disable reason.")
        private final String disableReason;

        public AccountFilter(Integer pageNumber, Integer pageSize, Boolean asc, Boolean locked, String lockReason,
                             Boolean enable, String disableReason) {
                super(pageNumber, pageSize, asc);
                this.locked = locked;
                this.lockReason = lockReason;
                this.enable = enable;
                this.disableReason = disableReason;
        }
}
