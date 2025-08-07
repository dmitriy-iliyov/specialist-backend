package com.specialist.auth.infrastructure.message.services;

import com.specialist.auth.infrastructure.message.models.PasswordRecoveryRequest;

public interface PasswordRecoveryService {
    void sendRecoveryCode(String recipientEmail);

    void recoverPasswordByCode(PasswordRecoveryRequest request);
}
