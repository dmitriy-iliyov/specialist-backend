package com.specialist.contracts.specialistdirectory;

import java.util.UUID;

public interface SystemBookmarkDeleteService {
    void deleteAllByOwnerId(UUID ownerId);
}
