package com.aidcompass.specialistdirectory.domain.bookmark;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, UUID> {
    long countByOwnerId(UUID ownerId);

    void deleteBySpecialist(SpecialistEntity entity);

    @EntityGraph(attributePaths = {"specialist", "specialist.type"})
    Slice<BookmarkEntity> findAllByOwnerId(UUID ownerId, Pageable pageable);

    void deleteAllByOwnerId(UUID ownerId);

    @Query("""
        SELECT s.id FROM BookmarkEntity b
        JOIN b.specialist s
        WHERE b.ownerId = :owner_id
    """)
    List<UUID> findAllSpecialistIdByOwnerId(@Param("owner_id") UUID ownerId);
}
