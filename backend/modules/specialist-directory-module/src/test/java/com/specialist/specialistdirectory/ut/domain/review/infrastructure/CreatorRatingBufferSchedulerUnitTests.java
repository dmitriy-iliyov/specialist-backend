package com.specialist.specialistdirectory.ut.domain.review.infrastructure;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.infrastructure.CreatorRatingBufferScheduler;
import com.specialist.specialistdirectory.domain.review.infrastructure.CreatorRatingBufferService;
import com.specialist.specialistdirectory.domain.review.infrastructure.CreatorRatingEventSender;
import com.specialist.specialistdirectory.domain.review.mappers.ReviewBufferMapper;
import com.specialist.specialistdirectory.domain.review.models.CreatorRatingBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatorRatingBufferSchedulerUnitTests {

    @Mock
    private CreatorRatingBufferService service;

    @Mock
    private ReviewBufferMapper mapper;

    @Mock
    private CreatorRatingEventSender eventSender;

    @InjectMocks
    private CreatorRatingBufferScheduler scheduler;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(scheduler, "CLEAN_BATCH_SIZE", 10);
        ReflectionTestUtils.setField(scheduler, "ACTUALIZE_BATCH_SIZE", 20L);
        ReflectionTestUtils.setField(scheduler, "TTL_AFTER_LAST_UPDATE", 60L);
    }

    @Test
    @DisplayName("UT: sendBatch() when events exist should send them")
    void sendBatch_eventsExist_shouldSend() {
        CreatorRatingBufferEntity entity = new CreatorRatingBufferEntity();
        CreatorRatingUpdateEvent event = new CreatorRatingUpdateEvent(UUID.randomUUID(), UUID.randomUUID(), 5, 1);
        
        when(service.findAllByDeliveryState(DeliveryState.READY_TO_SEND, 10)).thenReturn(List.of(entity));
        when(mapper.toEventList(List.of(entity))).thenReturn(List.of(event));

        scheduler.sendBatch();

        verify(eventSender).sendEvent(event, 1);
    }

    @Test
    @DisplayName("UT: sendBatch() when no events should do nothing")
    void sendBatch_noEvents_shouldDoNothing() {
        when(service.findAllByDeliveryState(DeliveryState.READY_TO_SEND, 10)).thenReturn(Collections.emptyList());
        when(mapper.toEventList(Collections.emptyList())).thenReturn(Collections.emptyList());

        scheduler.sendBatch();

        verify(eventSender, never()).sendEvent(any(), anyInt());
    }

    @Test
    @DisplayName("UT: actualizeState() should call service")
    void actualizeState_shouldCallService() {
        scheduler.actualizeState();

        verify(service).updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(
                eq(DeliveryState.PREPARE), 
                eq(DeliveryState.READY_TO_SEND), 
                any(Instant.class), 
                eq(20L)
        );
    }

    @Test
    @DisplayName("UT: actualizeState() when exception should rethrow")
    void actualizeState_exception_shouldRethrow() {
        doThrow(new RuntimeException("Error")).when(service)
                .updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(any(), any(), any(), any());

        assertThrows(RuntimeException.class, () -> scheduler.actualizeState());
    }
}
