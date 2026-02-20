package com.specialist.specialistdirectory.domain.type;

import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    boolean existsByTitleIgnoreCase(String title);

    Optional<TypeEntity> findByTitle(String title);

    Page<TypeEntity> findAllByIsApproved(boolean isApproved, Pageable pageable);

    Optional<TypeEntity> findByIdAndIsApproved(Long id, boolean isApproved);

    @Query("""
        SELECT t.id FROM TypeEntity t
        WHERE t.isApproved = :is_approved
    """)
    List<Long> findAllIdByIsApproved(@Param("is_approved") boolean isApproved);
}
