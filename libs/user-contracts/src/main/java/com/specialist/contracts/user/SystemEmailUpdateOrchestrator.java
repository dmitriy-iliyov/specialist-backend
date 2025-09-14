package com.specialist.contracts.user;

import java.util.UUID;

public interface SystemEmailUpdateOrchestrator {
    void updateById(ProfileType type, UUID id, String email);
}
