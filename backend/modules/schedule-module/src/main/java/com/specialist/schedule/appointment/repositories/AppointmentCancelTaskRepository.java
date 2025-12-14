package com.specialist.schedule.appointment.repositories;

import com.specialist.schedule.appointment.models.AppointmentCancelTaskEntity;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AppointmentCancelTaskRepository extends JpaRepository<AppointmentCancelTaskEntity, UUID> {
    @Query(value = """
        SELECT * FROM appointments_cancel_tasks
        WHERE type = :typeCode
        ORDER BY created_at
        LIMIT :batchSize
    """, nativeQuery = true)
    List<AppointmentCancelTaskEntity> findAllByType(@Param("typeCode") int typeCode, @Param("batchSize") int batchSize);

    boolean existsByParticipantIdAndTypeAndDate(UUID participantId, AppointmentCancelTaskType type, LocalDate date);

    void deleteAllByIdIn(Set<UUID> ids);

    @Query("""
        SELECT a.participantId FROM AppointmentCancelTaskEntity a
        WHERE a.type = :type AND a.participantId IN :participantIds
    """)
    List<UUID> existsByTypeAndParticipantIdIn(@Param("participantIds") Set<UUID> participantIds,
                                              @Param("type") AppointmentCancelTaskType type);
}
