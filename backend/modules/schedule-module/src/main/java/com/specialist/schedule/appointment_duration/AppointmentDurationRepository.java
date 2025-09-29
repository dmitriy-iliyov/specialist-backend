package com.specialist.schedule.appointment_duration;

import com.specialist.schedule.appointment_duration.models.AppointmentDurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AppointmentDurationRepository extends JpaRepository<AppointmentDurationEntity, Long> {
    Optional<AppointmentDurationEntity> findBySpecialistId(UUID specialistId);

    void deleteBySpecialistId(UUID specialistId);

    List<AppointmentDurationEntity> findAllBySpecialistIdIn(Set<UUID> specialistIds);
}
