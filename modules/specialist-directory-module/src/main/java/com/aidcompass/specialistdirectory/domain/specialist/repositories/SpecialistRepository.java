package com.aidcompass.specialistdirectory.domain.specialist.repositories;

import com.aidcompass.specialistdirectory.utils.pagination.SpecificationRepository;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Modifying(clearAutomatically = true)
    @Query(value = """
        UPDATE specialists
        SET type_id = :new_id, 
            suggested_type_id = null
        WHERE type_id = :old_id
          AND suggested_type_id = :new_id
    """, nativeQuery = true)
    void updateAllByTypeTitle(@Param("old_id") Long oldTypeId,
                              @Param("new_id") Long newTypeId);

    @Query(value = """
        SELECT creator_id FROM specialists
        WHERE id = :id
    """, nativeQuery = true)
    Optional<UUID> findCreatorIdById(@Param("id") UUID id);

    long countByCreatorId(UUID creatorId);
}
