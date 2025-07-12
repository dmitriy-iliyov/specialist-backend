package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkCountServiceImpl implements BookmarkCountService {
    
    private final BookmarkRepository repository;


    @Transactional(readOnly = true)
    @Override
    public long countByOwnerId(UUID ownerId) {
        return repository.countByOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UUID> findAllSpecialistIdByOwnerId(UUID ownerId) {
        return repository.findAllSpecialistIdByOwnerId(ownerId);
    }
}
