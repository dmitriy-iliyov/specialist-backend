package com.specialist.contracts.profile;

import java.util.UUID;

public interface SystemEmailUpdateOrchestrator {
    void updateById(ProfileType type, UUID id, String email);
}
