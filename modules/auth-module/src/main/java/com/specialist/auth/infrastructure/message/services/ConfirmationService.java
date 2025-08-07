package com.specialist.auth.infrastructure.message.services;

import java.util.UUID;

public interface ConfirmationService {
    void sendConfirmationCode(String email);
    String confirmEmailByCode(String code);
}
