package com.aidcompass.message.pass_recovery;

import java.util.Optional;

public interface PasswordRecoveryRepository {

    void save(String code, String recoveryResource);

    Optional<String> findAndDeleteByToken(String code);
}
