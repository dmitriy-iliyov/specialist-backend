package com.aidcompass.schedule.interval.repository;

import com.aidcompass.schedule.interval.models.IntervalEntity;
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

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<IntervalEntity> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    @Query("""
              SELECT i FROM IntervalEntity i
              WHERE i.ownerId = :owner_id
              AND i.date >= :start
              AND i.date <= :end
    """)
    List<IntervalEntity> findAllByOwnerIdAndDateInterval(@Param("owner_id") UUID ownerId,
                                                         @Param("start") LocalDate start,
                                                         @Param("end") LocalDate end);

    void deleteAllByOwnerId(UUID ownerId);

    Optional<IntervalEntity> findFirstByOwnerIdAndDateBetweenOrderByDateAscStartAsc(UUID ownerId, LocalDate start, LocalDate end);

    Optional<IntervalEntity> findByOwnerIdAndDateAndStart(UUID ownerId, LocalDate date, LocalTime start);

    @Query(value = """
        SELECT * FROM (
            SELECT i.*, ROW_NUMBER() OVER (PARTITION BY i.owner_id ORDER BY i.date ASC, i.start_t ASC) as rn
            FROM work_intervals i
            WHERE i.owner_id IN :owner_ids
              AND i.date BETWEEN :start AND :end
        ) sub
        WHERE sub.rn = 1
    """, nativeQuery = true)
    List<IntervalEntity> findAllNearestByOwnerIdIn(@Param("owner_ids") Set<UUID> ownerIds,
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
