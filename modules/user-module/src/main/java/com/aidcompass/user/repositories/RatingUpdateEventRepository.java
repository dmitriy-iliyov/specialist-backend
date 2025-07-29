package com.aidcompass.user.repositories;

import com.aidcompass.user.models.RatingUpdateEventEntity;
import com.aidcompass.user.models.enums.ProcessingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingUpdateEventRepository extends JpaRepository<RatingUpdateEventEntity, UUID> {
    Slice<RatingUpdateEventEntity> findAllByStatus(ProcessingStatus status, Pageable pageable);
}
