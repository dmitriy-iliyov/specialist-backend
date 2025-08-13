package com.specialist.user.infrastructure.rest;

import java.util.Set;
import java.util.UUID;

public interface ReviewBufferService {
    void deleteBatchByIds(Set<UUID> idsToDelete);

    void notifyToResend(Set<UUID> idsToResend);
}
