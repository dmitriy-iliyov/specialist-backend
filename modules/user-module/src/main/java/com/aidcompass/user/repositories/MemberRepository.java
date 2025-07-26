package com.aidcompass.user.repositories;

import com.aidcompass.user.models.MemberEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, UUID> {
    @NonNull
    @EntityGraph(attributePaths = {"avatar"})
    Optional<MemberEntity> findById(@NonNull UUID id);

    @EntityGraph(attributePaths = {"avatar"})
    List<MemberEntity> findAllByIdIn(Set<UUID> ids);
}
