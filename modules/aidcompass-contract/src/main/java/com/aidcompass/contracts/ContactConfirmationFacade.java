package com.aidcompass.contracts;

public interface ContactConfirmationFacade {

    void sendConfirmationMessage(String resource, Long resourceId, ContactType type) throws Exception;

    void validateConfirmationToken(String token, ContactType type);
}
