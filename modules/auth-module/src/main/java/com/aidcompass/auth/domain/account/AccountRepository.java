package com.aidcompass.auth.domain.account;

import com.aidcompass.auth.domain.account.models.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    boolean existsByEmail(String email);

    Optional<UUID> findIdByEmail(String email);

    Optional<AccountEntity> findByEmail(String email);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.isLocked = true
        WHERE a.id = :id
    """)
    void lockById(@Param("id") UUID id);
}
