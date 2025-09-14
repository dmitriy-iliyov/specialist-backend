package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.contracts.specialistdirectory.SystemBookmarkDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultSystemBookmarkDeleteService implements SystemBookmarkDeleteService {

    private final BookmarkService service;

    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        service.deleteAllByOwnerId(ownerId);
    }
}
