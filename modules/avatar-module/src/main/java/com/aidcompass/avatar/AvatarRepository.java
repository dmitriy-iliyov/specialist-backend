package com.aidcompass.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, UUID> {

    List<AvatarEntity> findAllByUserIdIn(Set<UUID> userIdList);

    void deleteByUserId(UUID userId);

    Optional<AvatarEntity> findByUserId(UUID userId);
}
