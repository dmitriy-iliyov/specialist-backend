package com.specialist.auth.domain.account.services;

public interface AccountPasswordRecoveryService {
    void recoverByEmail(String email, String password);
}
