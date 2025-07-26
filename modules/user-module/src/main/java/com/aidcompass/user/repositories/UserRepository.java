package com.aidcompass.user.repositories;

import com.aidcompass.user.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @NonNull
    @EntityGraph(attributePaths = {"avatar"})
    Optional<UserEntity> findById(@NonNull UUID id);

    @EntityGraph(attributePaths = {"avatar"})
    List<UserEntity> findAllByIdIn(Set<UUID> ids);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.avatarUrl = :avatar_url
        WHERE u.id = :id
    """)
    void updateAvatarUrlById(@Param("id") UUID id, @Param("avatar_url") String avatarUrl);
}
