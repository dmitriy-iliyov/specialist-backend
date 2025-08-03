package com.aidcompass.auth.infrastructure.message.services;

import com.aidcompass.auth.infrastructure.message.models.PasswordRecoveryRequest;

public interface PasswordRecoveryService {
    void sendRecoveryMessage(String recipientEmail);

    void recoverPassword(PasswordRecoveryRequest request);
}
