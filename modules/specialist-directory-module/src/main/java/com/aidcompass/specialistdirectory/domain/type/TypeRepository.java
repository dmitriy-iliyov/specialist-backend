package com.aidcompass.specialistdirectory.domain.type;

import com.aidcompass.specialistdirectory.domain.type.models.TypeEntity;
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

    List<TypeEntity> findAllByIsApproved(boolean isApproved);

    Optional<TypeEntity> findByIdAndIsApproved(Long id, boolean isApproved);

    @Query("""
        SELECT t.id FROM TypeEntity t
        WHERE t.isApproved = :is_approved
    """)
    List<Long> findAllIdByIsApproved(@Param("is_approved") boolean isApproved);
}
