package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.time.LocalDateTime;

public final class ExtendedPreAuthenticationChecker implements UserDetailsChecker {

    @Override
    public void check(UserDetails user) {
        AccountUserDetails accountUserDetails = (AccountUserDetails) user;
        if (!accountUserDetails.isAccountNonLocked()) {
            LocalDateTime term = InstantToLocalDataTimeConverter.convert(accountUserDetails.getLockTerm());
            throw new LockedException("Account is locked till " + term + ", reason " + accountUserDetails.getLockReason());
        } else if (!accountUserDetails.isEnabled()) {
            throw new DisabledException("Account is disabled, reason " + accountUserDetails.getDisableReason());
        } else if (!accountUserDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("Account has expired");
        }
    }
}
