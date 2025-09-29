package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    @Query("""
        SELECT rte.id FROM RefreshTokenEntity rte WHERE rte.accountId = :accountId
    """)
    List<UUID> findAllIdByAccountId(@Param("id") UUID accountId);

    void deleteAllByAccountId(UUID accountId);
}
