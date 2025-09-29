package com.specialist.schedule.interval.repository;

import com.specialist.schedule.interval.models.IntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IntervalRepository extends JpaRepository<IntervalEntity, Long> {

    void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date);

    List<IntervalEntity> findAllBySpecialistIdAndDate(UUID specialistId, LocalDate date);

    @Query("""
              SELECT i FROM IntervalEntity i
              WHERE i.specialistId = :specialistId
              AND i.date >= :start
              AND i.date <= :end
    """)
    List<IntervalEntity> findAllBySpecialistIdAndDateInterval(@Param("specialistId") UUID specialistId,
                                                              @Param("start") LocalDate start,
                                                              @Param("end") LocalDate end);

    void deleteAllBySpecialistId(UUID specialistId);

    Optional<IntervalEntity> findFirstBySpecialistIdAndDateBetweenOrderByDateAscStartAsc(UUID specialistId, LocalDate start, LocalDate end);

    Optional<IntervalEntity> findBySpecialistIdAndDateAndStart(UUID specialistId, LocalDate date, LocalTime start);

    @Query(value = """
        SELECT * FROM (
            SELECT i.*, ROW_NUMBER() OVER (PARTITION BY i.specialist_id ORDER BY i.date ASC, i.start_t ASC) as rn
            FROM work_intervals i
            WHERE i.specialistId IN :specialistIds
              AND i.date BETWEEN :start AND :end
        ) sub
        WHERE sub.rn = 1
    """, nativeQuery = true)
    List<IntervalEntity> findAllNearestBySpecialistIdIn(@Param("specialistIds") Set<UUID> specialistIds,
                                                        @Param("start") LocalDate start,
                                                        @Param("end") LocalDate end);

    @Modifying
    @Query(value = """ 
            WITH to_delete AS (
                SELECT id
                FROM work_intervals
                WHERE date < :weak_start
                LIMIT :batch_size)
            DELETE FROM work_intervals
            WHERE id IN (SELECT id FROM to_delete)
            RETURNING id
    """, nativeQuery = true)
    List<Long> deleteBatchBeforeDate(@Param("batch_size") int batchSize, @Param("weak_start") LocalDate weakStart);
}
