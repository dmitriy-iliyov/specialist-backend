package com.aidcompass.message.repositories;

import java.util.UUID;

public interface ConfirmationRepository {
    void save(String code, UUID accountId);
}
