package com.specialist.profile.repositories;

import com.specialist.profile.models.ShortProfileProjection;
import com.specialist.profile.models.UserProfileEntity;
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
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {
    @NonNull
    Optional<UserProfileEntity> findById(@NonNull UUID id);

    @Query("""
        SELECT u.id as id, u.type as type, u.firstName as firstName,
               u.secondName as secondName, u.lastName as lastName,
               u.avatarUrl as avatarUrl, u.creatorRating as creatorRating
        FROM UserProfileEntity u 
        WHERE u.id IN :ids
    """)
    List<ShortProfileProjection> findAllByIdIn(@Param("ids") Set<UUID> ids);

    @Modifying
    @Query("""
        UPDATE UserProfileEntity u
        SET u.avatarUrl = :avatar_url
        WHERE u.id = :id
    """)
    void updateAvatarUrlById(@Param("id") UUID id, @Param("avatar_url") String avatarUrl);

    @Modifying
    @Query("""
        UPDATE UserProfileEntity u
        SET u.email = : email
        WHERE u.id = : id
    """)
    void updateEmailById(@Param("id") UUID id, @Param("email") String email);

    void deleteAllByIdIn(List<UUID> ids);
}