package com.specialist.specialistdirectory.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, UUID>,
                                              JpaSpecificationExecutor<SpecialistEntity> {

    @Query("""
        SELECT s FROM SpecialistEntity s
        JOIN FETCH s.type
        WHERE s.id = :id
    """)
    Optional<SpecialistEntity> findWithTypeById(@Param("id") UUID id);

    @Query("""
        SELECT s FROM SpecialistEntity s
        JOIN FETCH s.info
        WHERE s.id = :id
    """)
    Optional<SpecialistEntity> findWithInfoById(@Param("id") UUID id);

    @Modifying(clearAutomatically = true)
    @Query(value = """
        UPDATE specialists
        SET type_id = :new_id, 
            suggested_type_id = null
        WHERE type_id = :old_id
          AND suggested_type_id = :new_id
    """, nativeQuery = true)
    void updateAllByTypeTitle(@Param("old_id") Long oldTypeId, @Param("new_id") Long newTypeId);

    @Query(value = """
        SELECT creator_id FROM specialists
        WHERE id = :id
    """, nativeQuery = true)
    Optional<UUID> findCreatorIdById(@Param("id") UUID id);

    long countByCreatorId(UUID creatorId);

    void updateStateById(UUID id, SpecialistState state);

    void updateStateAndOwnerIdById(UUID id, UUID ownerId, SpecialistState state);

    @Query("""
        SELECT new com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo(s.id, s.creatorId, s.ownerId, s.status)
        FROM SpecialistEntity s
        WHERE s.id = :id
    """)
    Optional<ShortSpecialistInfo> findShortInfoById(@Param("id") UUID id);

    @Query("""
        SELECT s.status FROM SpecialistEntity s
        WHERE s.id = :id
    """)
    Optional<SpecialistStatus> findStatusById(@Param("id") UUID id);

    @Query("""
        SELECT s.state FROM SpecialistEntity s
        WHERE s.id = :id
    """)
    Optional<SpecialistState> findStateById(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"type"})
    @Query("SELECT s FROM SpecialistEntity s WHERE s.id = :id AND s.state = :state")
    Optional<SpecialistEntity> findWithTypeByIdAndState(@Param("id") UUID id, @Param("state") SpecialistState state);

    void deleteByOwnerId(UUID ownerId);

    @Query("""
        SELECT new com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo(s.id, s.creatorId, s.ownerId, s.status)
        FROM SpecialistEntity s
        WHERE s.ownerId = :ownerId
    """)
    Optional<ShortSpecialistInfo> findShortInfoByOwnerId(@Param("ownerId") UUID ownerId);

    void updateStatusById(UUID id, SpecialistStatus status);
}
