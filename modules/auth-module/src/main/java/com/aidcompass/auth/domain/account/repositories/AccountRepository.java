package com.aidcompass.auth.domain.account.repositories;

import com.aidcompass.auth.domain.account.models.AccountEntity;
import com.aidcompass.auth.domain.account.models.enums.LockReason;
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

    Optional<UUID> findIdByEmail(String email);

    @EntityGraph(attributePaths = {"role", "authorities"})
    Optional<AccountEntity> findByEmail(String email);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.isLocked = true, a.lockReason = :reason, a.lockTerm = :lock_term
        WHERE a.id = :id
    """)
    void lockById(@Param("id") UUID id, @Param("reason") LockReason reason, @Param("lock_term") Instant lockTerm);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.isEnabled = true, a.unableReason = null
        WHERE a.id = :id
    """)
    void enableById(@Param("id") UUID id);

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    Page<AccountEntity> findAll(@NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    Page<AccountEntity> findAll(Specification<AccountEntity> specification, @NonNull Pageable pageable);
}
