package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.contracts.specialistdirectory.SystemCreatorRatingBufferService;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemCreatorRatingBufferServiceImpl implements SystemCreatorRatingBufferService {

    private final CreatorRatingBufferService service;

    @Override
    public void deleteAllByIdIn(Set<UUID> ids) {
        service.deleteAllByIdIn(ids);
    }

    @Override
    public void resendAllByIdIn(Set<UUID> ids) {
        service.updateAllDeliveryStateByIdIn(ids, DeliveryState.SENT);
    }
}
