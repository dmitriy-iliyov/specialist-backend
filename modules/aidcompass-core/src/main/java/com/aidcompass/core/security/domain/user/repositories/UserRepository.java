package com.aidcompass.core.security.domain.user.repositories;

import com.aidcompass.core.security.domain.user.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityById(UUID id);

    @EntityGraph(attributePaths = {"authorities"})
    List<UserEntity> findAllByIdIn(Set<UUID> ids);
}
