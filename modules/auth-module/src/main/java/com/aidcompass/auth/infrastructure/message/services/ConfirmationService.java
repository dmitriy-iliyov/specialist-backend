package com.aidcompass.auth.infrastructure.message.services;

import java.util.UUID;

public interface ConfirmationService {
    void sendConfirmationMessage(UUID id, String email);
    void confirmEmail(String code);
}
