package com.aidcompass.message;

import java.util.UUID;

public interface ConfirmationService {
    void sendConfirmationMessage(UUID id, String email);
}
