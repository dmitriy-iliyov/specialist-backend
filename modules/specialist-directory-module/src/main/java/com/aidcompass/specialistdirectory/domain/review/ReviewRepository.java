package com.aidcompass.specialistdirectory.domain.review;

import com.aidcompass.specialistdirectory.domain.review.models.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    @NonNull
    @EntityGraph(attributePaths = {"specialist"})
    Optional<ReviewEntity> findById(@NonNull UUID id);

    @Query("""
        SELECT r FROM ReviewEntity r
        WHERE r.specialist.id = :specialist_id
    """)
    Page<ReviewEntity> findAllBySpecialistId(@Param("specialist_id") UUID specialistId, Pageable pageable);
}
