package com.aidcompass.schedule.appointment_duration;

import com.aidcompass.schedule.appointment_duration.models.AppointmentDurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AppointmentDurationRepository extends JpaRepository<AppointmentDurationEntity, Long> {
    Optional<AppointmentDurationEntity> findByOwnerId(UUID ownerId);

    void deleteByOwnerId(UUID ownerId);

    List<AppointmentDurationEntity> findAllByOwnerIdIn(Set<UUID> ownerIds);
}
