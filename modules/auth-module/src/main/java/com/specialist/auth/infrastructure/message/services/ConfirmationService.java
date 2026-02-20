package com.specialist.auth.infrastructure.message.services;

public interface ConfirmationService {
    void sendConfirmationCode(String email);
    String confirmEmailByCode(String code);
}
