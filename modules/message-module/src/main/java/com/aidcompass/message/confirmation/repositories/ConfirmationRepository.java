package com.aidcompass.message.confirmation.repositories;

import java.util.Optional;


public interface ConfirmationRepository {

    void save(String key, String value, Long ttl);

    Optional<String> findAndDeleteByToken(String key);
}
