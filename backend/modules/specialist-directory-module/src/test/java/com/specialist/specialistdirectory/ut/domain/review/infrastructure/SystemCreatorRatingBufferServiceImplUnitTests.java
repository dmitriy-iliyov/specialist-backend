package com.specialist.specialistdirectory.ut.domain.review.infrastructure;

import com.specialist.specialistdirectory.domain.review.infrastructure.CreatorRatingBufferService;
import com.specialist.specialistdirectory.domain.review.infrastructure.SystemCreatorRatingBufferServiceImpl;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SystemCreatorRatingBufferServiceImplUnitTests {

    @Mock
    private CreatorRatingBufferService service;

    @InjectMocks
    private SystemCreatorRatingBufferServiceImpl systemService;

    @Test
    @DisplayName("UT: deleteAllByIdIn() should delegate")
    void deleteAllByIdIn_shouldDelegate() {
        Set<UUID> ids = Set.of(UUID.randomUUID());
        systemService.deleteAllByIdIn(ids);
        verify(service).deleteAllByIdIn(ids);
    }

    @Test
    @DisplayName("UT: resendAllByIdIn() should update state to SENT")
    void resendAllByIdIn_shouldUpdateState() {
        Set<UUID> ids = Set.of(UUID.randomUUID());
        systemService.resendAllByIdIn(ids);
        verify(service).updateAllDeliveryStateByIdIn(ids, DeliveryState.SENT);
    }
}
