package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    @Modifying
    @Query("""
        UPDATE RefreshTokenEntity t
        SET t.status = :status
        WHERE t.id = :id
    """)
    void updateStatusById(@Param("id") UUID id, @Param("status") RefreshTokenStatus status);

    boolean existsByIdAndStatus(UUID id, RefreshTokenStatus status);
}
