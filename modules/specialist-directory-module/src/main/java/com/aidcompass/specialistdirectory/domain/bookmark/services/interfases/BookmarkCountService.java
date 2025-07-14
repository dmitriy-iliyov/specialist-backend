package com.aidcompass.specialistdirectory.domain.bookmark.services.interfases;

import java.util.List;
import java.util.UUID;

public interface BookmarkCountService {
    long countByOwnerId(UUID ownerId);

    List<UUID> findAllSpecialistIdByOwnerId(UUID ownerId);
}
