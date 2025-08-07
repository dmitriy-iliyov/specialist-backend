package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceAccountRepository extends JpaRepository<ServiceAccountEntity, UUID> {
    @NonNull
    @EntityGraph(attributePaths = {"role", "authorities"})
    Optional<ServiceAccountEntity> findById(@NonNull UUID id);

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    Page<ServiceAccountEntity> findAll(@NonNull Pageable pageable);
}
