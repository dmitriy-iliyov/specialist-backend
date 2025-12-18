package com.specialist.auth.domain.account.models;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.validation.ValidEnum;
import lombok.Getter;

@Getter
public class AccountFilter extends PageRequest {

        private final Boolean locked;

        private final LockReason lockReason;

        private final Boolean enable;

        private final DisableReason disableReason;

        public AccountFilter(Integer pageNumber, Integer pageSize, Boolean asc, Boolean locked, LockReason lockReason,
                             Boolean enable, DisableReason disableReason) {
                super(pageNumber, pageSize, asc);
                this.locked = locked;
                this.lockReason = lockReason;
                this.enable = enable;
                this.disableReason = disableReason;
        }
}
