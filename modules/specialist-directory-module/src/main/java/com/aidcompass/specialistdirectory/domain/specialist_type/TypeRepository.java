package com.aidcompass.specialistdirectory.domain.specialist_type;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    boolean existsByTitleIgnoreCase(String title);

    Optional<TypeEntity> findByTitle(String title);

    List<TypeEntity> findAllByIsApproved(boolean isApproved);

    Optional<TypeEntity> findByIdAndIsApproved(Long id, boolean isApproved);
}
