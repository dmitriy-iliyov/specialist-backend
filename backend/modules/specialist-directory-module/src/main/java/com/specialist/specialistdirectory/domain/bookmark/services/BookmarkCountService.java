package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkIdPair;

import java.util.List;
import java.util.UUID;

public interface BookmarkCountService {
    long countByOwnerId(UUID ownerId);

    List<BookmarkIdPair> findAllIdPairByOwnerId(UUID ownerId);
}
