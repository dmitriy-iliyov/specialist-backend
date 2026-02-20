package com.specialist.auth.domain.account.services;

public interface AccountCacheService {
    void putEmailExistAs(String email, Boolean value);
}
