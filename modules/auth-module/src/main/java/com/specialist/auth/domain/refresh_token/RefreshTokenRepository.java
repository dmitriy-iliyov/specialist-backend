package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    @Query("""
        SELECT rte.id FROM RefreshTokenEntity rte WHERE rte.accountId = :accountId
    """)
    List<UUID> findAllIdByAccountId(@Param("id") UUID accountId);

    void deleteAllByAccountId(UUID accountId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
        WITH to_delete AS(
            SELECT id FROM refresh_tokens
            WHERE expires_at <= :threshold
            ORDER BY expires_at
            LIMIT :batchSize
            FOR UPDATE SKIP LOCKED
        )
        DELETE FROM refresh_tokens
        WHERE id IN (SELECT id FROM to_delete)
    """, nativeQuery = true)
    void deleteBatchByExpiredAThreshold(@Param("threshold") Instant threshold, @Param("batchSize") Integer batchSize);
}
