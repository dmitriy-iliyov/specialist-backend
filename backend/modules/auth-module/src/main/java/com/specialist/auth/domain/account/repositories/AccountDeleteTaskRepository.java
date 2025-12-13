package com.specialist.auth.domain.account.repositories;

import com.specialist.auth.domain.account.models.AccountDeleteTaskEntity;
import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountDeleteTaskRepository extends JpaRepository<AccountDeleteTaskEntity, UUID> {

    @Modifying
    @Query(value = """
        WITH to_lock AS(
            SELECT * FROM account_delete_tasks
            WHERE status = :status
            ORDER BY creeted_at
            LIMIT :batch_size
            FOR UPDATE SKIP LOCKED
        )
        UPDATE account_delete_tasks
        SET status = :lock_status
        WHERE id IN (SELECT id FROM to_lock)
        RETURNING *
    """, nativeQuery = true)
    List<AccountDeleteTaskEntity> findAndLockBatchByStatus(@Param("status") int statusCode,
                                                           @Param("lock_status") int lockStatusCode,
                                                           @Param("batch_size") int batchSize);

    @Modifying
    @Query(value = """
        UPDATE AccountDeleteTaskEntity e
        SET e.status = :status
        WHERE e.id IN :ids
    """)
    void updateBatchStatusByIdIn(@Param("status") AccountDeleteTaskStatus status, @Param("ids") Collection<UUID> ids);
}
