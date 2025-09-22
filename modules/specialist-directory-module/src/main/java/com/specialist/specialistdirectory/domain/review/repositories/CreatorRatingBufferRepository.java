package com.specialist.specialistdirectory.domain.review.repositories;

import com.specialist.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ReviewBufferRepository extends JpaRepository<ReviewBufferEntity, UUID> {
    Optional<ReviewBufferEntity> findByCreatorIdAndDeliveryState(UUID creatorId, DeliveryState state);

    Slice<ReviewBufferEntity> findAllByDeliveryState(DeliveryState state, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE ReviewBufferEntity rb
        SET rb.deliveryState = :delivery_state
        WHERE rb.id = :id
    """)
    void updateDeliveryStateById(@Param("id") UUID id, @Param("delivery_state") DeliveryState deliveryState);

    void deleteAllByIdIn(Set<UUID> ids);

    @Modifying
    @Query("""
        UPDATE ReviewBufferEntity rb
        SET rb.deliveryState = :delivery_state
        WHERE rb.id IN :ids
    """)
    void updateAllDeliveryStatesByIdIn(@Param("ids") Set<UUID> ids, @Param("delivery_state") DeliveryState deliveryState);
}
