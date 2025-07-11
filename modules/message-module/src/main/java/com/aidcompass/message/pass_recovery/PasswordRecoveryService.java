package com.aidcompass.message.pass_recovery;


public interface PasswordRecoveryService {

    void sendRecoveryMessage(String recipientResource) throws Exception;

    void recoverPassword(String code, String password);
}
