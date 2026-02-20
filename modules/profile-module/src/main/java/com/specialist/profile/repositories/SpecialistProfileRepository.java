package com.specialist.profile.repositories;

import com.specialist.profile.models.ShortProfileProjection;
import com.specialist.profile.models.SpecialistProfileEntity;
import com.specialist.profile.models.enums.SpecialistStatus;
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
public interface SpecialistProfileRepository extends JpaRepository<SpecialistProfileEntity, UUID> {
    @NonNull
    Optional<SpecialistProfileEntity> findById(@NonNull UUID id);

    @Query("""
        SELECT s.id as id, s.type as type, s.firstName as firstName,
               s.secondName as secondName, s.lastName as lastName,
               s.avatarUrl as avatarUrl, null as creatorRating
        FROM SpecialistProfileEntity s 
        WHERE s.id IN :ids
    """)
    List<ShortProfileProjection> findAllByIdIn(@Param("ids") Set<UUID> ids);

    @Modifying
    @Query("""
        UPDATE SpecialistProfileEntity s
        SET s.avatarUrl = :avatar_url
        WHERE s.id = :id
    """)
    void updateAvatarUrlById(@Param("id") UUID id, @Param("avatar_url") String avatarUrl);

    @Modifying
    @Query("""
        UPDATE SpecialistProfileEntity s
        SET s.email = : email
        WHERE s.id = : id
    """)
    void updateEmailById(@Param("id") UUID id, @Param("email") String email);

    Optional<SpecialistProfileEntity> findByIdAndStatus(UUID id, SpecialistStatus status);

    @Modifying
    @Query("UPDATE SpecialistProfileEntity s SET s.status = :status WHERE s.id = :id")
    void updateStatusById(@Param("id") UUID id, @Param("status") SpecialistStatus status);

    @Modifying
    @Query("UPDATE SpecialistProfileEntity s SET s.hasCard = true, s.cardId =: cardId")
    void updateCardId(@Param("cardId") UUID cardId);

    void deleteAllByIdIn(List<UUID> ids);
}
