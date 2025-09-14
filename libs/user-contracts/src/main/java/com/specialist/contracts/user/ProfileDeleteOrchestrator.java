package com.specialist.contracts.user;

import java.util.UUID;

public interface ProfileDeleteOrchestrator {
    void delete(UUID id, ProfileType type);
}
