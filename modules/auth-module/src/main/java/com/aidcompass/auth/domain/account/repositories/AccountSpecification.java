package com.aidcompass.auth.domain.account.repositories;

import com.aidcompass.auth.domain.account.models.AccountEntity;
import com.aidcompass.auth.domain.account.models.enums.LockReason;
import com.aidcompass.auth.domain.account.models.enums.UnableReason;
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

    public static Specification<AccountEntity> filterByUnableReason(UnableReason unableReason) {
        return (r, q, cb) -> unableReason == null ? null : cb.equal(r.get("unableReason"), unableReason);
    }
}
