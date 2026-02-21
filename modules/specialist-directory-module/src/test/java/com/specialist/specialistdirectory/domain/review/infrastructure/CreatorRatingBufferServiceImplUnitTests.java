package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.specialistdirectory.domain.review.models.CreatorRatingBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.repositories.CreatorRatingBufferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatorRatingBufferServiceImplUnitTests {

    @Mock
    private CreatorRatingBufferRepository repository;

    @InjectMocks
    private CreatorRatingBufferServiceImpl service;

    @BeforeEach
    void setUp() {
        service.RATING_BUFFER_SIZE = 10;
    }

    @Test
    @DisplayName("UT: updateByCreatorId() PERSIST when entity null should create new")
    void updateByCreatorId_persistNull_shouldCreate() {
        UUID creatorId = UUID.randomUUID();
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.empty());

        service.updateByCreatorId(creatorId, 5, OperationType.PERSIST);

        verify(repository).save(argThat(entity -> 
            entity.getCreatorId().equals(creatorId) &&
            entity.getSummaryRating() == 5 &&
            entity.getReviewCount() == 1 &&
            entity.getDeliveryState() == DeliveryState.PREPARE
        ));
    }

    @Test
    @DisplayName("UT: updateByCreatorId() PERSIST when entity exists should update")
    void updateByCreatorId_persistExists_shouldUpdate() {
        UUID creatorId = UUID.randomUUID();
        CreatorRatingBufferEntity entity = new CreatorRatingBufferEntity(creatorId, 10);
        entity.setReviewCount(5);
        
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.of(entity));

        service.updateByCreatorId(creatorId, 5, OperationType.PERSIST);

        assertEquals(15, entity.getSummaryRating());
        assertEquals(6, entity.getReviewCount());
        assertEquals(DeliveryState.PREPARE, entity.getDeliveryState());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updateByCreatorId() PERSIST when limit reached should set READY_TO_SEND")
    void updateByCreatorId_persistLimitReached_shouldSetReady() {
        UUID creatorId = UUID.randomUUID();
        CreatorRatingBufferEntity entity = new CreatorRatingBufferEntity(creatorId, 10);
        entity.setReviewCount(9);
        
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.of(entity));

        service.updateByCreatorId(creatorId, 5, OperationType.PERSIST);

        assertEquals(10, entity.getReviewCount());
        assertEquals(DeliveryState.READY_TO_SEND, entity.getDeliveryState());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updateByCreatorId() UPDATE when entity null should create new")
    void updateByCreatorId_updateNull_shouldCreate() {
        UUID creatorId = UUID.randomUUID();
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.empty());

        service.updateByCreatorId(creatorId, 5, OperationType.UPDATE);

        verify(repository).save(argThat(entity ->
                entity.getCreatorId().equals(creatorId) &&
                        entity.getSummaryRating() == 5 &&
                        entity.getReviewCount() == 1 && // Constructor sets 1
                        entity.getDeliveryState() == DeliveryState.PREPARE
        ));
    }

    @Test
    @DisplayName("UT: updateByCreatorId() UPDATE should update rating only")
    void updateByCreatorId_update_shouldUpdateRating() {
        UUID creatorId = UUID.randomUUID();
        CreatorRatingBufferEntity entity = new CreatorRatingBufferEntity(creatorId, 10);
        entity.setReviewCount(5);
        
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.of(entity));

        service.updateByCreatorId(creatorId, 2, OperationType.UPDATE);

        assertEquals(12, entity.getSummaryRating());
        assertEquals(5, entity.getReviewCount());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updateByCreatorId() DELETE when entity null should create new with negative values")
    void updateByCreatorId_deleteNull_shouldCreate() {
        UUID creatorId = UUID.randomUUID();
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.empty());

        service.updateByCreatorId(creatorId, 5, OperationType.DELETE);

        verify(repository).save(argThat(entity ->
                entity.getCreatorId().equals(creatorId) &&
                        entity.getSummaryRating() == -5 &&
                        entity.getReviewCount() == -1 &&
                        entity.getDeliveryState() == DeliveryState.PREPARE
        ));
    }

    @Test
    @DisplayName("UT: updateByCreatorId() DELETE should decrease rating and count")
    void updateByCreatorId_delete_shouldDecrease() {
        UUID creatorId = UUID.randomUUID();
        CreatorRatingBufferEntity entity = new CreatorRatingBufferEntity(creatorId, 10);
        entity.setReviewCount(5);
        
        when(repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)).thenReturn(Optional.of(entity));

        service.updateByCreatorId(creatorId, 2, OperationType.DELETE);

        assertEquals(8, entity.getSummaryRating());
        assertEquals(4, entity.getReviewCount());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updateDeliveryStateById() should delegate")
    void updateDeliveryStateById_shouldDelegate() {
        UUID id = UUID.randomUUID();
        service.updateDeliveryStateById(id, DeliveryState.SENT);
        verify(repository).updateDeliveryStateById(id, DeliveryState.SENT);
    }

    @Test
    @DisplayName("UT: findAllByDeliveryState() should delegate")
    void findAllByDeliveryState_shouldDelegate() {
        when(repository.findAllByDeliveryState(eq(DeliveryState.READY_TO_SEND), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        
        service.findAllByDeliveryState(DeliveryState.READY_TO_SEND, 10);
        
        verify(repository).findAllByDeliveryState(eq(DeliveryState.READY_TO_SEND), any(Pageable.class));
    }

    @Test
    @DisplayName("UT: deleteAllByIdIn() should delegate")
    void deleteAllByIdIn_shouldDelegate() {
        Set<UUID> ids = Set.of(UUID.randomUUID());
        service.deleteAllByIdIn(ids);
        verify(repository).deleteAllByIdIn(ids);
    }

    @Test
    @DisplayName("UT: deleteAllByIdIn() empty set should do nothing")
    void deleteAllByIdIn_empty_shouldDoNothing() {
        service.deleteAllByIdIn(Collections.emptySet());
        verify(repository, never()).deleteAllByIdIn(any());
    }

    @Test
    @DisplayName("UT: updateAllDeliveryStateByIdIn() should delegate")
    void updateAllDeliveryStateByIdIn_shouldDelegate() {
        Set<UUID> ids = Set.of(UUID.randomUUID());
        service.updateAllDeliveryStateByIdIn(ids, DeliveryState.SENT);
        verify(repository).updateAllDeliveryStatesByIdIn(ids, DeliveryState.SENT);
    }

    @Test
    @DisplayName("UT: updateAllDeliveryStateByIdIn() empty set should do nothing")
    void updateAllDeliveryStateByIdIn_empty_shouldDoNothing() {
        service.updateAllDeliveryStateByIdIn(Collections.emptySet(), DeliveryState.SENT);
        verify(repository, never()).updateAllDeliveryStatesByIdIn(any(), any());
    }

    @Test
    @DisplayName("UT: updateBatchDeliveryStateByDeliveryStateAndUpdateBefore() should delegate")
    void updateBatch_shouldDelegate() {
        Instant now = Instant.now();
        service.updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(DeliveryState.PREPARE, DeliveryState.READY_TO_SEND, now, 100L);
        verify(repository).updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(DeliveryState.PREPARE, DeliveryState.READY_TO_SEND, now, 100L);
    }
}
