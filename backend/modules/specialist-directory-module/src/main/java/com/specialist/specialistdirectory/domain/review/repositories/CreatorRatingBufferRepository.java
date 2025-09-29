package com.specialist.specialistdirectory.domain.review.repositories;

import com.specialist.specialistdirectory.domain.review.models.CreatorRatingBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CreatorRatingBufferRepository extends JpaRepository<CreatorRatingBufferEntity, UUID> {
    Optional<CreatorRatingBufferEntity> findByCreatorIdAndDeliveryState(UUID creatorId, DeliveryState state);

    Slice<CreatorRatingBufferEntity> findAllByDeliveryState(DeliveryState state, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE CreatorRatingBufferEntity crbe
        SET crbe.deliveryState = :delivery_state
        WHERE crbe.id = :id
    """)
    void updateDeliveryStateById(@Param("id") UUID id, @Param("delivery_state") DeliveryState deliveryState);

    void deleteAllByIdIn(Set<UUID> ids);

    @Modifying
    @Query("""
        UPDATE CreatorRatingBufferEntity crbe
        SET crbe.deliveryState = :delivery_state
        WHERE crbe.id IN :ids
    """)
    void updateAllDeliveryStatesByIdIn(@Param("ids") Set<UUID> ids, @Param("delivery_state") DeliveryState deliveryState);

    @Modifying
    @Query(value = """
        WITH to_update AS(
            SELECT id FROM creator_rating_buffers
            WHERE update_at < :before_date AND delivery_state = :old_state
            LIMIT :batch_size
        )
        UPDATE creator_rating_buffers
            SET delivery_state = :new_state
        WHERE id IN (SELECT id FROM to_update)
    """, nativeQuery = true)
    int updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(@Param("old_state") DeliveryState oldState,
                                                               @Param("new_state") DeliveryState newState,
                                                               @Param("before_date") Instant beforeDate,
                                                               @Param("batch_size") Long batchSize);
}
