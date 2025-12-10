package com.specialist.schedule.appointment.repositories;

import com.specialist.schedule.appointment.models.AppointmentCancelTaskEntity;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentCancelTaskRepository extends JpaRepository<AppointmentCancelTaskEntity, UUID> {
    Optional<AppointmentCancelTaskEntity> findByParticipantIdAndTypeAndDate(UUID participantId,
                                                                            AppointmentCancelTaskType type,
                                                                            LocalDate date);

    @Query(value = """
        SELECT * FROM appointments_cancel_tasks
        WHERE type = :type
        ORDER BY date
        LIMIT 1
        FOR UPDATE SKIP LOCKED
    """, nativeQuery = true)
    Optional<AppointmentCancelTaskEntity> findFirstByType(AppointmentCancelTaskType type);

    boolean existsByParticipantIdAndTypeAndDate(UUID participantId, AppointmentCancelTaskType type, LocalDate date);
}
