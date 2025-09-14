package com.specialist.user.repositories;

import com.specialist.user.models.ShortProfileProjection;
import com.specialist.user.models.SpecialistEntity;
import com.specialist.user.models.enums.SpecialistStatus;
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
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, UUID> {
    @NonNull
    Optional<SpecialistEntity> findById(@NonNull UUID id);

    @Query("""
        SELECT s.id as id, s.type as type, s.firstName as firstName,
               s.secondName as secondName, s.lastName as lastName,
               s.avatarUrl as avatarUrl, null as creatorRating
        FROM SpecialistEntity s 
        WHERE s.id IN :ids
    """)
    List<ShortProfileProjection> findAllByIdIn(@Param("ids") Set<UUID> ids);

    @Modifying
    @Query("""
        UPDATE SpecialistEntity s
        SET s.avatarUrl = :avatar_url
        WHERE s.id = :id
    """)
    void updateAvatarUrlById(@Param("id") UUID id, @Param("avatar_url") String avatarUrl);

    @Modifying
    @Query("""
        UPDATE SpecialistEntity s
        SET s.email = : email
        WHERE s.id = : id
    """)
    void updateEmailById(@Param("id") UUID id, @Param("email") String email);

    Optional<SpecialistEntity> findByIdAndStatus(UUID id, SpecialistStatus status);

    void updateStatusById(UUID id, SpecialistStatus status);

    @Modifying
    @Query("UPDATE SpecialistEntity s SET s.hasCard = true, s.cardId =: cardId")
    void updateCardId(@Param("cardId") UUID cardId);
}
