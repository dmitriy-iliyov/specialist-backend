package com.aidcompass.message.services;

import java.util.UUID;

public interface ConfirmationService {
    void sendConfirmationMessage(UUID id, String email);
}
