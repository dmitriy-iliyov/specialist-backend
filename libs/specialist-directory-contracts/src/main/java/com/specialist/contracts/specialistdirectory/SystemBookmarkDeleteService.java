package com.specialist.contracts.specialistdirectory;

import java.util.UUID;

public interface SystemBookmarkService {
    void deleteAllByOwnerId(UUID ownerId);
}
