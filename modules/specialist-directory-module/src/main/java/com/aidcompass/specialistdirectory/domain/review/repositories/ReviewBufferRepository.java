package com.aidcompass.specialistdirectory.domain.review.repositories;

import com.aidcompass.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.aidcompass.specialistdirectory.domain.review.models.enums.DeliveryState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewBufferRepository extends JpaRepository<ReviewBufferEntity, UUID> {
    Optional<ReviewBufferEntity> findByCreatorIdAndDeliveryState(UUID creatorId, DeliveryState state);

    void deleteByCreatorId(UUID creatorId);

    Slice<ReviewBufferEntity> findAllByDeliveryState(DeliveryState state, Pageable pageable);
}
