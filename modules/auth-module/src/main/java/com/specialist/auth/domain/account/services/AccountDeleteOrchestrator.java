package com.specialist.auth.domain.account.services;

import java.util.UUID;

public interface AccountDeleteOrchestrator {
    void delete(UUID id);
}
