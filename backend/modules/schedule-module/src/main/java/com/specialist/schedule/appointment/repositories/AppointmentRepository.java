package com.specialist.schedule.appointment.repositories;


import com.specialist.schedule.appointment.models.AppointmentEntity;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>,
                                               JpaSpecificationExecutor<AppointmentEntity> {

    List<AppointmentEntity> findAllBySpecialistIdAndDateAndStatus(UUID specialistId, LocalDate date, AppointmentStatus status);

    List<AppointmentEntity> findAllByUserIdAndDateAndStatus(UUID userId, LocalDate date, AppointmentStatus status);

    @Query("""
        SELECT a FROM AppointmentEntity a
        WHERE a.specialistId = :specialistId
        AND a.date BETWEEN :start AND :end
    """)
    List<AppointmentEntity> findAllBySpecialistIdAndDateInterval(@Param("specialistId") UUID specialistId,
                                                                 @Param("start") LocalDate start,
                                                                 @Param("end") LocalDate end);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        WITH to_update AS(
            SELECT id FROM appointments
            WHERE status = :old_status
            AND (
                specialist_id = ANY(:participant_ids)
                OR user_id = ANY(:participant_ids)
            )
            ORDER BY date
            LIMIT :batch_size
            FOR UPDATE SKIP LOCKED
        )
        UPDATE appointments
            SET status = :new_status
        WHERE id IN (SELECT id FROM to_update)
        RETURNING *;
    """, nativeQuery = true)
    List<AppointmentEntity> updateAllStatusByStatusAndParticipantIdIn(@Param("batch_size") int batchSize,
                                                                      @Param("participant_ids") UUID [] participantIds,
                                                                      @Param("old_status") Integer oldStatus,
                                                                      @Param("new_status") Integer newStatus);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        WITH to_update AS(
            SELECT id FROM appointments
            WHERE (specialist_id = :participant_id OR user_id = :participant_id) 
              AND date = :date AND status = :old_status
            ORDER BY date
            LIMIT :batch_size
        )
        UPDATE appointments
            SET status = :new_status
        WHERE id IN (SELECT id FROM to_update)
        RETURNING *;
    """, nativeQuery = true)
    List<AppointmentEntity> updateAllStatusByStatusAndDateAndParticipantId(@Param("batch_size") int batchSize,
                                                                           @Param("old_status") Integer oldStatus,
                                                                           @Param("participant_id") UUID participantId,
                                                                           @Param("date") LocalDate date,
                                                                           @Param("new_status") Integer status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        WITH to_update AS(
            SELECT id FROM appointments
            WHERE date = :date AND status = :old_status
            AND (
                specialist_id = ANY(:participant_ids)
                OR user_id = ANY(:participant_ids)
            )
            ORDER BY date
            LIMIT :batch_size
            FOR UPDATE SKIP LOCKED
        )
        UPDATE appointments
            SET status = :new_status
        WHERE id IN (SELECT id FROM to_update)
        RETURNING *;
    """, nativeQuery = true)
    List<AppointmentEntity> updateAllStatusByStatusAndDateAndParticipantIdIn(@Param("batch_size") int batchSize,
                                                                             @Param("old_status") Integer oldStatus,
                                                                             @Param("participant_ids") UUID [] participantIds,
                                                                             @Param("date") LocalDate date,
                                                                             @Param("new_status") Integer status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        WITH to_update AS (
            SELECT id FROM appointments
            WHERE date < :date_limit
            AND status = :old_status
            ORDER BY date
            LIMIT :batch_size
            FOR UPDATE SKIP LOCKED
        )
        UPDATE appointments
        SET status = :new_status
        WHERE id IN (SELECT id FROM to_update)
        RETURNING id
    """, nativeQuery = true)
    List<Long> updateAllStatusByStatusAndBeforeDate(@Param("batch_size") int batchSize,
                                                    @Param("old_status") Integer oldStatus,
                                                    @Param("date_limit") LocalDate dateLimit,
                                                    @Param("new_status") Integer newStatus);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        WITH to_select AS (
            SELECT * FROM appointments
            WHERE date = :date AND status = :status AND process_status = :old_process_status
            ORDER BY date
            LIMIT :batch_size
            FOR UPDATE SKIP LOCKED
        )
        UPDATE appointments
        SET process_status = :new_process_status
        WHERE id IN (SELECT id FROM to_select)
        RETURNING *
    """, nativeQuery = true)
    List<AppointmentEntity> findAllByDateAndStatusAndProcessStatus(@Param("date") LocalDate date,
                                                                   @Param("status") Integer status,
                                                                   @Param("old_process_status") String oldProcessStatus,
                                                                   @Param("batch_size") int batchSize,
                                                                   @Param("new_process_status") String newProcessStatus);
}