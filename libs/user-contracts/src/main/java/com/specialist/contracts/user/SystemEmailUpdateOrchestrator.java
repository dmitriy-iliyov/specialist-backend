package com.specialist.user.services.system;

import com.specialist.contracts.user.UserType;

import java.util.UUID;

public interface SystemEmailUpdateOrchestrator {
    void updateById(UserType type, UUID id, String email);
}
