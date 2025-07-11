package com.aidcompass.schedule.appointment.repositories;


import com.aidcompass.schedule.appointment.models.AppointmentEntity;
import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    List<AppointmentEntity> findAllByVolunteerIdAndDateAndStatus(UUID volunteerId, LocalDate date, AppointmentStatus status);

    List<AppointmentEntity> findAllByCustomerIdAndDateAndStatus(UUID customerId, LocalDate date, AppointmentStatus status);

    @Query("""
        SELECT a FROM AppointmentEntity a
        WHERE a.volunteerId = :volunteer_id
        AND a.date BETWEEN :start AND :end
    """)
    List<AppointmentEntity> findAllByVolunteerIdAndDateInterval(@Param("volunteer_id") UUID volunteerId,
                                                                @Param("start") LocalDate start,
                                                                @Param("end") LocalDate end);

    @Modifying
    @Query(value = """
        DELETE FROM appointments
        WHERE volunteer_id = :participant_id
           OR customer_id = :participant_id
        RETURNING id
    """, nativeQuery = true)
    List<Long> deleteAllByParticipantId(@Param("participant_id") UUID participantId);

    @Modifying
    @Query(value = """
        UPDATE appointments
        SET status = :status
        WHERE (volunteer_id = :participant_id OR customer_id = :participant_id)
          AND date = :date
        RETURNING id
    """, nativeQuery = true)
    List<Long> updateAllStatus(@Param("participant_id") UUID participantId,
                               @Param("date") LocalDate date,
                               @Param("status") AppointmentStatus status);

    @Modifying(clearAutomatically = true)
    @Query(value = """
        WITH to_update AS (
            SELECT id FROM appointments
            WHERE date < :date_limit 
            AND status = 0
            LIMIT :batch_size
        )
        UPDATE appointments
        SET status = 3
        WHERE id IN (SELECT id FROM to_update)
        RETURNING id
    """, nativeQuery = true)
    List<Long> markBatchAsSkipped(@Param("batch_size") int batchSize, @Param("date_limit") LocalDate dateLimit);

    @Query(value = """
        SELECT a FROM AppointmentEntity a
        WHERE a.date = :scheduled_date
        AND a.status = :status
    """)
    Slice<AppointmentEntity> findBatchToRemind(@Param("scheduled_date") LocalDate scheduledDate,
                                               @Param("status") AppointmentStatus status, Pageable pageable);
}