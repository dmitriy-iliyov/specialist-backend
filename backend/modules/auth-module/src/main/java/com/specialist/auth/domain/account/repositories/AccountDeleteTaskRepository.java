package com.specialist.auth.domain.account.repositories;

import com.specialist.auth.domain.account.models.AccountDeleteTaskEntity;
import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT e FROM AccountDeleteTaskEntity e WHERE e.status = :status")
    List<AccountDeleteTaskEntity> findBatchByStatus(@Param("status") AccountDeleteTaskStatus status, Pageable pageable);

    @Modifying
    @Query(value = """
        UPDATE AccountDeleteTaskEntity e
        SET e.status = :status
        WHERE e.id IN :ids
    """)
    void updateBatchStatusByIdIn(@Param("status") AccountDeleteTaskStatus status, @Param("ids") Collection<UUID> ids);
}
