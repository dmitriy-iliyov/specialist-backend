package com.aidcompass.user.infrastructure;

import java.util.Set;
import java.util.UUID;

public interface ReviewBufferService {
    void deleteBatchByIds(Set<UUID> idsToDelete);
}
