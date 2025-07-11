package com.aidcompass.message.confirmation.services;

public interface ResourceConfirmationService {

    String getType();

    void sendConfirmationMessage(String resource, Long resourceId) throws Exception;

    void validateConfirmationToken(String token);
}
