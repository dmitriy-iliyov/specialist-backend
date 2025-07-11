package com.aidcompass.users.detail;

import com.aidcompass.users.detail.models.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DetailRepository extends JpaRepository<DetailEntity, UUID> {

    Optional<DetailEntity> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
