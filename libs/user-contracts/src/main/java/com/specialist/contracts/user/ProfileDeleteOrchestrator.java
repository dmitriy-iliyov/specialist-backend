package com.specialist.contracts.user;

import java.util.UUID;

public interface UserDeleteOrchestrator {
    void delete(UUID id, UserType type);
}
