package com.specialist.profile.infrastructure.rest;

import java.util.Set;
import java.util.UUID;

public interface CreatorRatingBufferService {
    void deleteBatchByIds(Set<UUID> idsToDelete);

    void notifyToResend(Set<UUID> idsToResend);
}
