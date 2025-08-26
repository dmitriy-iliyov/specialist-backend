package com.specialist.auth.domain.account.repositories;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<AccountEntity> filterByIsLocked(Boolean isLocked) {
        return (r, q, cb) -> isLocked == null ? null : cb.equal(r.get("isLocked"), isLocked);
    }

    public static Specification<AccountEntity> filterByLockeReason(LockReason lockReason) {
        return (r, q, cb) -> lockReason == null ? null : cb.equal(r.get("lockReason"), lockReason);
    }

    public static Specification<AccountEntity> filterByIsEnable(Boolean isEnabled) {
        return (r, q, cb) -> isEnabled == null ? null : cb.equal(r.get("isEnabled"), isEnabled);
    }

    public static Specification<AccountEntity> filterByUnableReason(DisableReason disableReason) {
        return (r, q, cb) -> disableReason == null ? null : cb.equal(r.get("disableReason"), disableReason);
    }
}
