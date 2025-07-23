package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkRepository;
import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkIdPair;
import com.aidcompass.specialistdirectory.domain.bookmark.services.interfases.BookmarkCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkCountServiceImpl implements BookmarkCountService {
    
    private final BookmarkRepository repository;


    @Cacheable(value = "specialists:bookmarks:count:total", key = "#ownerId")
    @Transactional(readOnly = true)
    @Override
    public long countByOwnerId(UUID ownerId) {
        return repository.countByOwnerId(ownerId);
    }

    @Cacheable(value = "specialists:bookmarks:id_pair", key = "#ownerId")
    @Transactional(readOnly = true)
    @Override
    public List<BookmarkIdPair> findAllIdPairByOwnerId(UUID ownerId) {
        return repository.findAllIdPairByOwnerId(ownerId);
    }
}
