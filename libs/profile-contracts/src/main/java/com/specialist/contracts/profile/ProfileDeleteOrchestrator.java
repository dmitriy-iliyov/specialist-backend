package com.specialist.contracts.profile;

import java.util.UUID;

public interface ProfileDeleteOrchestrator {
    void delete(UUID id, ProfileType type);
}
