package com.specialist.schedule.appointment_duration;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface AppointmentDurationService {
    Long set(UUID specialistId, Long duration);

    Long findBySpecialistId(UUID specialistId);

    Map<UUID, Long> findAllBySpecialistIdIn(Set<UUID> specialistIds);

    void deleteBySpecialistId(UUID specialistId);

    void deleteAllBySpecialistIds(Set<UUID> specialistIds);
}
