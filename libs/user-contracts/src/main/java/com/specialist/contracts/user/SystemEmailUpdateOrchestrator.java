package com.specialist.contracts.user;

import java.util.UUID;

public interface SystemEmailUpdateOrchestrator {
    void updateById(UserType type, UUID id, String email);
}
