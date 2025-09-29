package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;

import java.util.UUID;

public interface ProfileDeleteStrategy {
    void deleteById(UUID id);
    ProfileType getType();
}
