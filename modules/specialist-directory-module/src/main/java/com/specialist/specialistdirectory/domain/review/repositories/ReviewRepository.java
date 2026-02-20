package com.specialist.specialistdirectory.domain.review.repositories;

import com.specialist.specialistdirectory.domain.review.models.ReviewEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
        WHERE r.specialist.id = :specialistId AND r.status =: status
    """)
    Page<ReviewEntity> findAllBySpecialistIdAndStatus(@Param("specialistId") UUID specialistId,
                                                      @Param("status") ReviewStatus status,
                                                      Pageable pageable);

    @Modifying
    @Query("""
        UPDATE ReviewEntity r
        SET r.status = :status, r.approver = :approver
        WHERE r.id = :id
    """)
    void updateStatusById(@Param("id") UUID id, @Param("status") ReviewStatus status, @Param("approver") ApproverType approver);

    @Modifying
    @Query("""
        UPDATE ReviewEntity r
        SET r.pictureUrl = :pictureUrl
        WHERE r.id = :id
    """)
    void updatePictureUrlById(@Param("id") UUID id, @Param("pictureUrl") String pictureUrl);
}
