package com.aidcompass.auth.infrastructure.message.services;

import com.aidcompass.auth.infrastructure.message.models.PasswordRecoveryRequest;

public interface PasswordRecoveryService {
    void sendRecoveryCode(String recipientEmail);

    void recoverPasswordByCode(PasswordRecoveryRequest request);
}
