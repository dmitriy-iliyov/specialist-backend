package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;

import java.util.UUID;

public interface ProfileDeleteOrchestrator {
    void delete(UUID id, ProfileType type);
}
