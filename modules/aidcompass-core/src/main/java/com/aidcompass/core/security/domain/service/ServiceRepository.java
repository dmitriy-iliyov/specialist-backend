package com.aidcompass.core.security.domain.service;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {
    @EntityGraph(attributePaths = {"authorityEntity"})
    Optional<ServiceEntity> findByServiceName(String serviceName);
}
