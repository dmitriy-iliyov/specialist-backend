package com.aidcompass.schedule.appointment_duration;

import com.aidcompass.core.security.domain.authority.models.Authority;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface AppointmentDurationService {
    Long set(UUID ownerId, Authority authority, Long duration);

    Long findByOwnerId(UUID ownerId);

    Map<UUID, Long> findAllByOwnerIdIn(Set<UUID> ownerId);

    void deleteByOwnerId(UUID ownerId);
}
