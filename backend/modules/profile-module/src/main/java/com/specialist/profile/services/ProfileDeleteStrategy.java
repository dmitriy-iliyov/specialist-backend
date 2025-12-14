package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;

import java.util.List;
import java.util.UUID;

public interface ProfileDeleteStrategy {
    void deleteAllByIds(List<UUID> ids);
    ProfileType getType();
}
