package com.specialist.specialistdirectory.domain.bookmark;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkEntity;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkIdPair;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, UUID> {

    boolean existsByOwnerIdAndSpecialistId(UUID ownerId, UUID specialistId);

    long countByOwnerId(UUID ownerId);

    @EntityGraph(attributePaths = {"specialist", "specialist.type"})
    Slice<BookmarkEntity> findAllByOwnerId(UUID ownerId, Pageable pageable);

    @Query("""
        SELECT new com.specialist.specialistdirectory.domain.bookmark.models.BookmarkIdPair(b.id, s.id) 
        FROM BookmarkEntity b
        JOIN b.specialist s
        WHERE b.ownerId = :owner_id
    """)
    List<BookmarkIdPair> findAllIdPairByOwnerId(@Param("owner_id") UUID ownerId);

    void deleteAllByOwnerId(UUID ownerId);

    void deleteAllByOwnerIdIn(Set<UUID> ownerIds);
}
