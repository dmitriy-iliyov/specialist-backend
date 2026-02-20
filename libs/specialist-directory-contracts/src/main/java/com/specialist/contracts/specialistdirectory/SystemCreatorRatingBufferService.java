package com.specialist.contracts.specialistdirectory;

import java.util.Set;
import java.util.UUID;

public interface SystemCreatorRatingBufferService {
    void deleteAllByIdIn(Set<UUID> ids);
    void resendAllByIdIn(Set<UUID> ids);
}
