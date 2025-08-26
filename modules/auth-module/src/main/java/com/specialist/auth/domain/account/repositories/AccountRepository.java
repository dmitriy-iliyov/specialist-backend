package com.specialist.auth.domain.account.repositories;

import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID>, JpaSpecificationExecutor<AccountEntity> {
    boolean existsByEmail(String email);

    @Query("""
        SELECT a.id FROM AccountEntity a
        WHERE a.email = :email
    """)
    Optional<UUID> findIdByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"role", "authorities"})
    Optional<AccountEntity> findByEmail(String email);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.isEnabled = true, a.disableReason = null
        WHERE a.email = :email
    """)
    void enableByEmail(@Param("email") String email);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.isLocked = true, a.lockReason = :reason, a.lockTerm = :lock_term
        WHERE a.id = :id
    """)
    void lockById(@Param("id") UUID id, @Param("reason") LockReason reason, @Param("lock_term") Instant lockTerm);

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    Page<AccountEntity> findAll(@NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    Page<AccountEntity> findAll(Specification<AccountEntity> specification, @NonNull Pageable pageable);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.isEnabled = false, a.disableReason = :reason WHERE a.id = :id")
    void disableById(@Param("id") UUID id, @Param("reason") DisableReason reason);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.isEnabled = true, a.disableReason = null WHERE a.id = :id")
    void enableById(@Param("id") UUID id);

    @Query("""
        SELECT a.provider FROM AccountEntity a
        WHERE a.email = :email
    """)
    Optional<Provider> findProviderByEmail(@Param("email") String email);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.isLocked = false, a.lockReason = null, a.lockTerm = null
        WHERE a.id = :id
    """)
    void unlockById(UUID id);
}
