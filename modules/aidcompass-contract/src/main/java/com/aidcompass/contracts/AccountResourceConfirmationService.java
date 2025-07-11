package com.aidcompass.contracts;

public interface AccountResourceConfirmationService {
    void sendConfirmationMessage(String resource) throws Exception;

    void validateConfirmationToken(String token);
}
